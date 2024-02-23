package enstabretagne.cureTatooine.scenarios;

import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.engine.ScenarioInitData;

public class ScenarioTatooineInit extends ScenarioInitData{
	
	public static class AffluenceParMois{
		LogicalDateTime d;
		double tauxMax;
		public AffluenceParMois(LogicalDateTime d, double tauxMax) {
			super();
			this.d = d;
			this.tauxMax = tauxMax;
		}
		
		@Override
		public String toString() {
			return "Debut = " + d + " Taux="+tauxMax;
		}
	}

	public List<AffluenceParMois> affluenceParMois;
	public int capaciteMaxClientInstitut;
	
	public ScenarioTatooineInit(String name, int graine, LogicalDateTime debut, LogicalDateTime fin,
			List<AffluenceParMois> affluenceIni,int capaciteMaxClientInstitut) {
		super(name, graine, debut, fin);
		affluenceParMois = affluenceIni;
		this.capaciteMaxClientInstitut = capaciteMaxClientInstitut;
	}

}
