import java.util.concurrent.atomic.AtomicLong;

public class Simulation {
    
    private int id;
    private int threads;
    private AtomicLong iterations;

    private AtomicLong currentIteration;

    private boolean isRunning;

    private Worker[] workers;

    public Runnable getRun() {
        return run;
    }

    public void setRun(Runnable run) {
        this.run = run;
    }

    private Runnable run;
    
    public Simulation(int id, int threads, int iterations){
        this.id = id;
        this.threads = threads;
        this.iterations = new AtomicLong(iterations);
        this.currentIteration = new AtomicLong(0);
        this.run = null;
        workers = new Worker[threads];
    }

    private void iterate(){
        isRunning = true;
        workers = new Worker[threads];
        initializeWorkers();
        startWorkers();
        isRunning = false;
    }

    public void start(Runnable run){
        this.run = run;
        iterate();
    }

    private void startWorkers(){
        for (int i = 0; i < workers.length; i++) {
            workers[i].start();
        }

        for (int i = 0; i < workers.length; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeWorkers(){
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker(i, this);
        }
    }
    
    public void abort(){
        
    }
    
    public void pause(){
        
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
