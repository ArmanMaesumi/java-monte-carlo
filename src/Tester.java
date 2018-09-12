import java.util.concurrent.atomic.AtomicInteger;

public class Tester implements MonteCarlo{

    public AtomicInteger x = new AtomicInteger(0);

    @Override
    public void run() {
        //System.out.println(sim.getIteration());
        if (isPrime((int)sim.getIteration()))
            x.incrementAndGet();
    }

    @Override
    public void initialize(Object o) {
        long start = System.currentTimeMillis();
        sim.setIterations(1000000);
        sim.setThreads(10);
        sim.start(this::run);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(x.get());
    }

    private synchronized static boolean isPrime(int num) {
        if (num % 2 == 0) return false;
        for (int i = 3; i * i < num; i += 2)
            if (num % i == 0) return false;
        System.out.println(num);
        return true;
    }

}
