import java.util.concurrent.atomic.AtomicLong;

public class Worker implements Runnable {

    private boolean working;

    private AtomicLong completedIterations;
    private int threadId;

    private Simulation sim;
    private long start;
    private long end;

    public Worker(int id, long start, long end, Simulation sim) {
        this.threadId = id;
        this.sim = sim;
        this.start = start;
        this.end = end;
        this.working = false;
        this.completedIterations = new AtomicLong(0);
    }

    public void run() {
        working = true;
        System.out.println("Thread("+threadId+"): Start="+start+", End="+end);
        for (long i = start; i < end; i++) {
            sim.getEnvironment().run(i);
            sim.incrementIteration();
            incrementCompletedIterations();
        }
        working = false;
        System.out.println("Thread("+threadId+") Complete: " + completedIterations.get());
    }

    private void incrementCompletedIterations() {
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
