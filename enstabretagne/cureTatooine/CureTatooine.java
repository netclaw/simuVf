package enstabretagne.cureTatooine;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.engine.SimplePlanMonitor;

public class CureTatooine {

	public static void main(String[] args) {
		
		System.out.println("début code");
		Logger.load();
		
		
		LogicalDateTime start = new LogicalDateTime("01/01/2024 00:00");
		LogicalDateTime end = new LogicalDateTime("31/09/2024 00:00");

		PlanTatooine plan = new PlanTatooine(1, start, end);

		SimplePlanMonitor spm = new SimplePlanMonitor(plan);
		spm.run();
		
		System.out.println("fin code");
	}

}
