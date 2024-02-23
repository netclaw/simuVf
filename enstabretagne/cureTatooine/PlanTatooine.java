package enstabretagne.cureTatooine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.cureTatooine.scenarios.ScenarioTatooineInit;
import enstabretagne.cureTatooine.scenarios.ScenarioTatooineInit.AffluenceParMois;
import enstabretagne.cureTatooine.scenarios.ScenarioTatooineSimple;
import enstabretagne.engine.Plan;
import enstabretagne.engine.Scenario;

public class PlanTatooine extends Plan{

	LogicalDateTime debut;
	LogicalDateTime fin;
	
	LinkedList<Scenario> tatooineSCs;

	public PlanTatooine(int nbReplique, LogicalDateTime debut, LogicalDateTime fin) {
		super(nbReplique);
		this.debut = debut;
		this.fin = fin;
		tatooineSCs = new LinkedList<Scenario>();
	}

	@Override
	public void initScenarii() {
		
		System.out.println("debut PlanTatooine:initScenarii");
		
		var affluenceIni = new ArrayList<ScenarioTatooineInit.AffluenceParMois>();
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/03/2024 00:00"),0.5));
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/04/2024 00:00"),0.6));
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/05/2024 00:00"),0.7));
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/06/2024 00:00"),0.8));
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/07/2024 00:00"),0.95));
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/08/2024 00:00"),0.9));
		affluenceIni.add(new AffluenceParMois(new LogicalDateTime("01/09/2024 00:00"),0.65));


		for (int i = 0; i < getNbReplique(); i++) {
			tatooineSCs.add(new ScenarioTatooineSimple(getEngine(), 
					new ScenarioTatooineInit("MonScenarioSimple"+i,i, debut, fin,
							affluenceIni,
							180)));
		}
		
		System.out.println("fin PlanTatooine:initScenarii");

	}

	@Override
	public boolean hasNextScenario() {
		return tatooineSCs.size()>0;
	}

	@Override
	public Scenario nextScenario() {
		if(hasNextScenario()) {
			Scenario sc = tatooineSCs.pop();
			engine.setCurrentScenario(sc);
			return sc;
		}
		return null;
	}

}
