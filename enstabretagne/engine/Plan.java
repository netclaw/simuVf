package enstabretagne.engine;

/**le plan tel que proposé dans ce TD est défini comme un générateur de scénarii
il comprend 3 méthodes abstraites à spécialiser*/

public abstract class Plan {
	
	//nombre de fois qu'un même scénario sera exécuté mais avec une graine différente
	private int nbReplique;
	public int getNbReplique() {
		return nbReplique;
	}
	
	protected SimuEngine engine;
	public SimuEngine getEngine() {
		return engine;
	}
	
	//numéro de la réplique actuelle
	private int currentRepliqueNumber;
	public int getCurrentRepliqueNumber() {
		return currentRepliqueNumber;
	}
	
	public Plan(int nbReplique) {
		this.nbReplique=nbReplique;
	}
	//cette méthode permet de définir chaque scénario à exécuter
	//elle présuppose que votre implémentation saura stocker la définition de chaque scénario
	public abstract void initScenarii();
	
	//cette méthode permet de savoir s'il y a encore un scénario à exécuter
	public abstract boolean hasNextScenario();
	
	//cette méthode permet de récupérer le prochain scénario. 
	//renvoie null sinon
	public abstract Scenario nextScenario(); 

}
