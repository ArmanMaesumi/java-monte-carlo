import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ThreadLocalRandom;

public class Tester implements MonteCarlo {

    public AtomicInteger x = new AtomicInteger(0);
    public AtomicInteger sum = new AtomicInteger(0);
    public ThreadLocalRandom rand;

    @Override
    public void defaultSimulation() {
        x.incrementAndGet();
    }

    @Override
    public void domainSimulation(long i) {
        sum.addAndGet(
                rand.current().nextInt(1, 10) *
                        rand.current().nextInt(1, 10));
    }

    @Override
    public void initialize() {
        rand = ThreadLocalRandom.current();
        long start = System.currentTimeMillis();
        sim.setEnvironment(this);
        sim.setIterations(100000);
        sim.setThreads(2);
        sim.setMode(SIMULATION_MODE_DOMAIN);
        sim.start();
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) / 1000.0);
        System.out.println("Iterations Completed: " + sim.getCurrentIteration());
        System.out.println("Sum: " + sum.get());
        System.out.println(sum.get() / (sim.getIterations() * 1.0));
    }

    private boolean isPrime(long num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        //System.out.println(num);
        return true;
    }

}
