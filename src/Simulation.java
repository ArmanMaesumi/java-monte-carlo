import java.util.concurrent.atomic.AtomicLong;

public class Simulation {
    
    private int id;
    private int threads;
    private int iterations;

    private AtomicLong currentIteration;

    private boolean isRunning;

    private Worker[] workers;
    
    public Simulation(int id, int threads, int iterations){
        this.id = id;
        this.threads = threads;
        this.iterations = iterations;
        this.currentIteration = new AtomicLong(0);

        workers = new Worker[threads];
    }

    public void iterate(Runnable run){
        if (iterations > currentIteration.get()){
            incrementIteration();
            run.run();
        }else{
            isRunning = false;
        }
    }

    public void start(){
        isRunning = true;
    }
    
    public void abort(){
        
    }
    
    public void pause(){
        
    }

    public String getStatus(){
        return currentIteration.get() / iterations * 1.0 + "%";
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

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    
    public boolean isRunning(){
        return isRunning;
    }
}
