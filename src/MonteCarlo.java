public interface MonteCarlo {

    Simulation sim = new Simulation(0, 1, 1);

    void run(long i);
    void initialize(Object o);

}
