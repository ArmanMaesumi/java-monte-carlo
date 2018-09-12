import java.util.concurrent.atomic.AtomicLong;

public class Worker extends Thread {

    private boolean working;

    private AtomicLong completedIterations;
    private int threadId;

    private Simulation sim;

    public Worker(int id, Simulation sim) {
        this.threadId = id;
        this.sim = sim;

        this.working = false;
        this.completedIterations = new AtomicLong(0);
    }

    public void run() {
        working = true;
        while (sim.getIterations() > sim.getIteration()) {
            sim.getRun().run();
            sim.incrementIteration();
            incrementCompletedIterations();
        }
        working = false;
        System.out.println("Thread: " + threadId + ", " + completedIterations.get());
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
