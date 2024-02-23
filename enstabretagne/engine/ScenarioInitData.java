package enstabretagne.engine;

import enstabretagne.base.time.LogicalDateTime;

/**Modéle de données d'initialisation minimal d'un scénario
simple POJO**/
public class ScenarioInitData extends InitData {
	int graine; 
	LogicalDateTime debut; 
	LogicalDateTime fin;
	
	public ScenarioInitData(String name,int graine, LogicalDateTime debut, LogicalDateTime fin) {
		super(name);
		this.debut=debut;
		this.fin=fin;
		this.graine = graine;
	}
	
	public int getGraine() {
		return graine;
	}
	public LogicalDateTime getDebut() {
		return debut;
	}
	public LogicalDateTime getFin() {
		return fin;
	}
}
