public interface MonteCarlo {

    Simulation sim = new Simulation(0, 1, 1, 0);

    int SIMULATION_MODE_DEFAULT = 0;
    int SIMULATION_MODE_DOMAIN = 1;

    void defaultSimulation();
    void domainSimulation(long i);

    void initialize();

}
