import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Simulation {

    private int id;
    private int threads;
    private int mode;

    private boolean isRunning;

    private AtomicLong iterations;
    private AtomicLong currentIteration;

    private Worker[] workers;
    private Class[] workerTypes = {DomainWorker.class};
    private ExecutorService threadPool;

    private MonteCarlo environment;

    public Simulation() {
        this.id = 0;
        this.threads = 1;
        this.mode = MonteCarlo.SIMULATION_MODE_DEFAULT;
        this.iterations = new AtomicLong(1);
        this.currentIteration = new AtomicLong(0);
        this.threadPool = Executors.newFixedThreadPool(threads);
        this.workers = new Worker[threads];
    }

    public Simulation(int id, int threads, int iterations, int mode) {
        this.id = id;
        this.threads = threads;
        this.mode = mode;
        this.iterations = new AtomicLong(iterations);
        this.currentIteration = new AtomicLong(0);
        this.threadPool = Executors.newFixedThreadPool(threads);
        this.workers = new Worker[threads];
    }

    public void start() {
        workers = new Worker[threads];
        initializeWorkers();
        startWorkers();
    }

    private void initializeWorkers(){
        long jobSize = iterations.get() / threads;
        long remainder = iterations.get() % threads;
        System.out.println("---");
        System.out.println(jobSize);
        System.out.println(remainder);
        System.out.println("Mode="+mode);
        System.out.println("---");

        // Default:
        if (mode == 0) {
            for (int i = 0; i < workers.length; i++) {
                if (i == workers.length - 1 && remainder > 0) {
                    workers[i] = new Worker(i, this, jobSize + remainder);
                } else {
                    workers[i] = new Worker(i, this, jobSize);
                }
            }
        }
        // Domain:
        else if (mode == 1) {
            for (int i = 0; i < workers.length; i++) {
                if (i == workers.length - 1 && remainder > 0) {
                    workers[i] = new DomainWorker(i, i * (jobSize), ((i + 1) * jobSize) + remainder, this);
                } else {
                    workers[i] = new DomainWorker(i, i * (jobSize), (i + 1) * (jobSize), this);
                }
            }
        }
    }

    private void startWorkers() {
        isRunning = true;
        for (int i = 0; i < workers.length; i++) {
            threadPool.execute(workers[i]);
        }
        shutdown();
        isRunning = false;
    }

    private void shutdown(){
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Thread pool termination error : " + e.toString());
        }
    }

    public void abort() {
        for (int i = 0; i < workers.length; i++) {
            if(workers[i].isWorking())
                workers[i].abort();
        }
    }

    public void pause() {
        for (int i = 0; i < workers.length; i++) {
            if(workers[i].isWorking())
                workers[i].pause();
        }
    }

    public void unpause(){
        for (int i = 0; i < workers.length; i++) {
            if(!workers[i].isWorking())
                workers[i].unpause();
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    public void setEnvironment(MonteCarlo mc) {
        this.environment = mc;
    }

    public MonteCarlo getEnvironment() {
        return this.environment;
    }

    public String getStatus() {
        if (currentIteration.get() <= 0)
            return "0.0%";
        return ((1.0 * currentIteration.get()) / iterations.get()) * 100 + "%";
    }

    public void incrementIteration() {
        this.currentIteration.incrementAndGet();
    }

    public void decrementIteration() {
        this.currentIteration.decrementAndGet();
    }

    public void setIteration(long n) {
        this.currentIteration.addAndGet(n);
    }

    public long getCurrentIteration() {
        return this.currentIteration.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        if (!isRunning) {
            this.threads = threads;
            this.threadPool = Executors.newFixedThreadPool(threads);
        }
    }

    public long getIterations() {
        return iterations.get();
    }

    public void setIterations(long iterations) {
        this.iterations.set(iterations);
    }

    public boolean isRunning() {
        return isRunning;
    }
}
