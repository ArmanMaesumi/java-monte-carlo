import java.util.concurrent.atomic.AtomicLong;

public class Worker implements Runnable {

    protected boolean working;

    private long iterations;
    protected AtomicLong completedIterations;
    protected Simulation sim;

    protected int threadId;

    public Worker(int id, Simulation sim) {
        this.threadId = id;
        this.working = false;
        this.sim = sim;
        this.completedIterations = new AtomicLong(0);
    }

    public Worker(int id, Simulation sim, long iterations){
        this.threadId = id;
        this.iterations = iterations;
        this.working = false;
        this.sim = sim;
        this.completedIterations = new AtomicLong(0);
    }

    public void run() {
        working = true;
        System.out.println("Thread(" + threadId + "): Iterations=" + iterations);
        for (long i = 0; i < iterations; i++) {
            sim.getEnvironment().defaultSimulation();
            sim.incrementIteration();
            incrementCompletedIterations();
        }
        working = false;
        System.out.println("Thread(" + threadId + ") Complete: " + completedIterations.get());
    }

    public void incrementCompletedIterations() {
        this.completedIterations.incrementAndGet();
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public long getCompletedIterations() {
        return completedIterations.get();
    }

    public void setCompletedIterations(long completedIterations) {
        this.completedIterations.set(completedIterations);
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }
}
