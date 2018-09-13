import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Tester implements MonteCarlo {

    public AtomicInteger x = new AtomicInteger(0);

    @Override
    public void defaultSimulation() {
        x.incrementAndGet();
    }

    @Override
    public void domainSimulation(long i) {
        if (isPrime(i))
            x.incrementAndGet();
    }

    @Override
    public void initialize() {
        long start = System.currentTimeMillis();
        sim.setEnvironment(this);
        sim.setIterations(10000000);
        sim.setThreads(10);
        sim.setMode(SIMULATION_MODE_DOMAIN);
        sim.start();
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) / 1000.0);
        System.out.println(x.get());
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
