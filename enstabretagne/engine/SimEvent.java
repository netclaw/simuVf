package enstabretagne.engine;

import enstabretagne.base.time.LogicalDateTime;

/**
 * Evenement de simulation � surcharger
 *
 */

public abstract class SimEvent implements Comparable<SimEvent> {
	//r�f�rence � l'entit� porteuse de l'�v�nement
	protected EntiteSimulee entitePorteuseEvenement;
	//instant d'occurence de l'�v�nement
	private LogicalDateTime d;
	protected LogicalDateTime getDateOccurence()
	{
		return d;
	}

	//replanification de l'�v�nement
	public void rescheduleAt(LogicalDateTime d)
	{
		this.d=d;
	}
	public SimEvent(LogicalDateTime d) {
		this.d=d;
	}
	
	@Override
	public int compareTo(SimEvent ev) {
		return this.d.compareTo(ev.d);
	}

	//m�thode � surcharger pour ex�cuter une action
	public abstract void process();

}
