package enstabretagne.engine;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.basics.ScenarioId;

/** entité simulée particulière
 le scénario est la mère de toutes les entités
utile si on veut créer des tests particuliers ou faire des plans d'expérience
il a une méthode particulière de création d'entités simulées
il expose les données génériques d'initialisation d'un scénario à savoir la date de début de fin et la graine */
public abstract class Scenario extends EntiteSimulee{

	public LogicalDateTime getDebut() {
		return ((ScenarioInitData) getInit()).getDebut();
	}
	public LogicalDateTime getFin() {
		return ((ScenarioInitData) getInit()).getFin();
	}
	
	
	public Scenario(SimuEngine engine,ScenarioInitData init) {
		super(engine,init);
		id = new ScenarioId(init.getName(),init.getGraine());
		graine = ((ScenarioInitData) getInit()).getGraine();
	}

	//gestion de la graine
	private int graine;
	public int getGraine() {
		return graine;
	}
	public void setGraine(int graine) {
		id.setRepliqueNumber(graine);
		this.graine = graine;
	}

	//identifiant du scénario
	//on l'identifie par un nom sous forme de chaine de caractère
	//il est porteur de la graine utilisée dans le cadre d'une réplique
	ScenarioId id;
	ScenarioId getID() {
		return id;
	}
	
	public abstract void creerEntitesSimulees();

}
