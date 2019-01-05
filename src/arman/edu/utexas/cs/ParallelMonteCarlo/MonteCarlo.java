package arman.edu.utexas.cs.ParallelMonteCarlo;

public interface MonteCarlo {

    // arman.edu.utexas.cs.ParallelMonteCarlo.Simulation object used to control simulation properties
    Simulation sim = new Simulation(0, 1, 1, 0);

    // Constants that determine simulation mode
    int SIMULATION_MODE_DEFAULT = 0;
    int SIMULATION_MODE_DOMAIN = 1;

    // Corresponding simulation methods
    void defaultSimulation();
    void domainSimulation(long i);

    void initialize();
}
