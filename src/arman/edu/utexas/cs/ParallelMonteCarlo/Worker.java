package arman.edu.utexas.cs.ParallelMonteCarlo;

import java.util.concurrent.atomic.AtomicLong;

class Worker implements Runnable {

    protected boolean working;
    protected boolean abort;
    protected boolean pause;

    private long iterations;
    protected AtomicLong completedIterations;
    protected Simulation sim;

    protected int threadId;


    public Worker(int id, Simulation sim) {
        this.threadId = id;
        this.working = false;
        this.abort = false;
        this.pause = false;
        this.sim = sim;
        this.completedIterations = new AtomicLong(0);
    }

    public Worker(int id, Simulation sim, long iterations){
        this.threadId = id;
        this.iterations = iterations;
        this.working = false;
        this.abort = false;
        this.pause = false;
        this.sim = sim;
        this.completedIterations = new AtomicLong(0);
    }

    public void run() {
        working = true;
        abort = false;
        pause = false;
        System.out.println("Thread(" + threadId + "): Iterations=" + iterations);
        for (long i = 0; i < iterations;) {
            if (abort)
                break;

            if (!pause) {
                sim.getEnvironment().defaultSimulation();
                sim.incrementIteration();
                incrementCompletedIterations();
                i++;
            }
        }
        working = false;
        abort = false;
        pause = false;
        System.out.println("Thread(" + threadId + ") Complete: " + completedIterations.get());
    }

    public void incrementCompletedIterations() {
        this.completedIterations.incrementAndGet();
    }

    public boolean isWorking() {
        return this.working;
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
        return this.threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public void abort(){
        System.out.println("Thread("+threadId+") Aborted.");
        this.abort = true;
        this.working = false;
    }

    public void pause(){
        System.out.println("Thread("+threadId+") Paused.");
        this.pause = true;
    }

    public void unpause(){
        System.out.println("Thread("+threadId+") Unpaused.");
        this.pause = false;
    }

}
