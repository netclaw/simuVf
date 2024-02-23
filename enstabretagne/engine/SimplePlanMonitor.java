package enstabretagne.engine;

import enstabretagne.base.logger.Logger;

/**
 * exemple d'un moniteur ultra simple
 * 
 */
//c'est lui qui porte le cadencement des sc�narios via le plan d'exp�rience
public class SimplePlanMonitor {
	Plan plan;
	SimuEngine engine;

	public SimplePlanMonitor(Plan p) {
		this.engine = new SimuEngine();
		
		plan = p;
		plan.engine = engine;
	}

	public void run() {
		
		System.out.println("début run SImplePlanMonitor");
		
		plan.initScenarii();
		//Logger.Information(this, "main", "Début du plan d'expérience");
		System.out.println("début du plan dexpérience");

		while (plan.hasNextScenario()) {
			plan.nextScenario().creerEntitesSimulees();
			
			engine.initSimulation();
			engine.simulate();
			engine.terminate(false);
		}
		engine.terminate(true);
		//Logger.Information(null, "main", "Fin du plan d'exp�rience");
		System.out.println("fin du plan dexpérience");
		Logger.Terminate();
		
		System.out.println("fin run SImplePlanMonitor");

	}

}
