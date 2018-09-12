import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Simulation {
    
    private int id;
    private int threads;
    private AtomicLong iterations;

    private AtomicLong currentIteration;

    private boolean isRunning;

    private Worker[] workers;

    private ExecutorService threadPool;

    private MonteCarlo environment;

    public Simulation(int id, int threads, int iterations){
        this.id = id;
        this.threads = threads;
        this.iterations = new AtomicLong(iterations);
        this.currentIteration = new AtomicLong(0);
        this.threadPool = Executors.newFixedThreadPool(threads);
        workers = new Worker[threads];
    }

    public void start(){
        isRunning = true;
        workers = new Worker[threads];
        initializeWorkers();
        startWorkers();
        isRunning = false;
    }

    private void startWorkers(){
        for (int i = 0; i < workers.length; i++) {
            threadPool.execute(workers[i]);
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Thread pool termination error : " + e.toString());
        }
    }

    private void initializeWorkers(){
        long jobSize = iterations.get() / threads;
        long remainder = iterations.get() % threads;
        for (int i = 0; i < workers.length; i++) {
            if (i == workers.length - 1 && remainder > 0){
                workers[i] = new Worker(i, i * (jobSize), remainder, this);
            }else{
                workers[i] = new Worker(i, i * (jobSize), (i + 1) * (jobSize), this);
            }
            //workers[i] = new Worker(i, this);
        }
    }
    
    public void abort(){
        
    }
    
    public void pause(){
        
    }

    public void setEnvironment(MonteCarlo mc){
        this.environment = mc;
    }

    public MonteCarlo getEnvironment(){
        return this.environment;
    }

    public String getStatus(){
        return currentIteration.get() / iterations.get() * 1.0 + "%";
    }

    public void incrementIteration(){
        this.currentIteration.incrementAndGet();
    }

    public void decrementIteration(){
        this.currentIteration.decrementAndGet();
    }

    public void setIteration(long n){
        this.currentIteration.addAndGet(n);
    }

    public long getIteration(){
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
        this.threads = threads;
        this.threadPool = Executors.newFixedThreadPool(threads);
    }

    public long getIterations() {
        return iterations.get();
    }

    public void setIterations(long iterations) {
        this.iterations.set(iterations);
    }
    
    public boolean isRunning(){
        return isRunning;
    }
}
