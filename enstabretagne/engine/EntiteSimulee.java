package enstabretagne.engine;

import java.util.List;
import java.util.function.Predicate;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;

/**
 * 
 * EntiteSimulee est l'entit� dont il faut h�riter pour cr�er des entit�s.
 *
 */
public abstract class EntiteSimulee {
	enum EtatEntite {NONE,INITIALIZED,DEAD};
	
	private SimuEngine engine;
	private InitData ini;
	
	//�tat de l'entit�
	private EtatEntite etat;
	protected EtatEntite getEtat() {
		return etat;
	}
	
	public EntiteSimulee(SimuEngine engine,InitData ini) {
		this.etat= EtatEntite.NONE;
		this.engine=engine;
		this.ini=ini;
		engine.mesEntitesSimulees.add(this);
	}
	
	public SimuEngine getEngine() {
		return engine;
	}
	
	public String getName() {
		return ini.getName();
	}
	public InitData getInit() {
		return ini;
	}
	
	public void Post(SimEvent ev) {
		ev.entitePorteuseEvenement = this;
		engine.Post(ev);
	}
	public void UnPost(SimEvent ev) {
		engine.UnPost(ev);
	}
	
	public LogicalDateTime Now() {
		return engine.getCurrentDate();
	}
	
	public List<EntiteSimulee> recherche(Predicate<EntiteSimulee> query) {
		return engine.recherche(query);
	}
	
	public MoreRandom getRandomGenerator() {
		return engine.getRandomGenerator();
	}
	
	/**seule m�thode vraiment publique
	elle �vite de pouvoir appeler plusieurs fois l'initialisation	
	attention: quand une entit� parente cr�e une entit� fille c'est � elle qu'incombe 
	de demander son initialisation via le requestInit().
	en effet, le moteur ne peut pas savoir quand il est opportun de d�clencher l'init pour cette entit�*/
	
	public void requestInit() {
		if(etat==EtatEntite.NONE) init();
	}

	//m�thode � surcharger	
	
	protected void init() {
		etat = EtatEntite.INITIALIZED;
	}
	//m�thode � surcharger
	//lors de cet appel dans la surcharge on vide proprement les listes
	//on met � null tous les objets de mani�re � aider le garbage collector
	protected void terminate() {
		etat = EtatEntite.DEAD;
	}
}
