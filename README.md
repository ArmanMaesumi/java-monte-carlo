# java-monte-carlo
A Java library for multithreaded Monte Carlo simulations.

See Tester.java for sample code

## To Implement MonteCarlo interface:

```
// Implement MonteCarlo interface:
public class Tester implements MonteCarlo {

		// Code for example in defaultSimulation():
		public AtomicInteger sum = new AtomicInteger(0);
    
    	// MonteCarlo interface method:
    	// SIMULATION_MODE_DEFAULT:
    	public void defaultSimulation() {
	// This method will preform n number of iterations of this method on set number of threads.
			// In this simulation mode, the method does not have access to the current
			// iteration of the simulation. You should use the default simulation mode
			// when you are doing n number of INDEPENDANT simulations, meaning the current
			// simulation, n_i, does not effect the output of this method.

			// Example 1:
			// Concurrently calculate the average value of random(0, 10) * random (0, 10):
			// Use AtomicInteger, and ThreadLocalRandom classes for safe concurrent use:
			int a = ThreadLocalRandom.current().nextInt(1, 10);
			int b = ThreadLocalRandom.current().nextInt(1, 10);

			// Accumulate a*b in AtomicInteger:
			// AtomicLong may be more suited depending on your number of iterations.
			sum.addAndGet(a * b);
			// See Example 1 code in initialize()
    	}
    
    	// MonteCarlo interface method:
 		// SIMULATION_MODE_DOMAIN:
    	public void domainSimulation(long i) {
    		// This method acts similarly to defaultSimulation(), the primary difference is that
			// domain simulations are provided the current iteration that is being processed.
			// This means that the value "long i" will attain every value from 0 to n, where
			// n is the total number of iterations.

			// Example 2:
			// Concurrently print all primes from 0 to n.

			if (isPrime(i))
				System.out.println(i + " is a prime.");
    	}
    
    	// MonteCarlo interface method:
    	public void initialize() {
			// Set simulation environment to this class instance:
			sim.setEnvironment(this);

			// Set number of iterations to perform:
			sim.setIterations(100000);

			// Set number of threads to use in simuation:
			sim.setThreads(2);

			// Set the simulation mode:
			sim.setMode(SIMULATION_MODE_DEFAULT);
			// or
			// sim.setMode(SIMULATION_MODE_DOMAIN);

			// Run simulation:
			sim.start();

			// Example 1 code:
			// Print ratio of total value of a*b to the total number of iterations.
			// Force double precision result with *1.0:
			System.out.println("Average value of a*b = " + sum.get / (sim.getIterations() * 1.0));
    	}
}
```
