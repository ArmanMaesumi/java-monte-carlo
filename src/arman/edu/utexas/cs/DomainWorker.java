package arman.edu.utexas.cs;

class DomainWorker extends Worker {

    private long start;
    private long end;

    public DomainWorker(int id, long start, long end, Simulation sim) {
        super(id, sim);
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        working = true;
        abort = false;
        pause = false;
        System.out.println("Thread(" + threadId + "): Start=" + start + ", End=" + end);
        for (long i = start; i < end;) {
            if (abort)
                break;

            if (!pause) {
                sim.getEnvironment().domainSimulation(i);
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
}
