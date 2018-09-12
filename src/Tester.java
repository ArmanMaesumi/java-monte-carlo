public class Tester implements MonteCarlo{

    @Override
    public void run() {
        System.out.println(sim.getIterations());
    }

    @Override
    public void initialize(Object o) {
        sim.start();
        while (sim.isRunning())
            sim.iterate(this::run);
    }

}
