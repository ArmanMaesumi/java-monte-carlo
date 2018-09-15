package arman.edu.utexas.cs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadLocalRandom;

class Tester implements MonteCarlo {

    public AtomicInteger x = new AtomicInteger(0);
    public AtomicInteger sum = new AtomicInteger(0);

    public void defaultSimulation() {
        x.incrementAndGet();
        int i = ThreadLocalRandom.current().nextInt(1,10);
    }

    public void domainSimulation(long i) {
        sum.addAndGet(
                ThreadLocalRandom.current().nextInt(1, 10) *
                        ThreadLocalRandom.current().nextInt(1, 10));
    }

    public void initialize() {
        long start = System.currentTimeMillis();
        MonteCarlo.sim.setEnvironment(this);
        MonteCarlo.sim.setIterations(100000);
        MonteCarlo.sim.setThreads(2);
        MonteCarlo.sim.setMode(MonteCarlo.SIMULATION_MODE_DOMAIN);
        MonteCarlo.sim.start();
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) / 1000.0);
        System.out.println("Iterations Completed: " + MonteCarlo.sim.getCurrentIteration());
        System.out.println("Sum: " + sum.get());
        System.out.println(sum.get() / (MonteCarlo.sim.getIterations() * 1.0));
    }

    private boolean isPrime(long num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
    }

}
