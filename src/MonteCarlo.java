public interface MonteCarlo {

    Simulation sim = new Simulation(0, 1, 1);

    void run();
    void initialize(Object o);

}
