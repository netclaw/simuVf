package enstabretagne.cureTatooine.scenarios;

import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.cureTatooine.client.Client;
import enstabretagne.cureTatooine.client.ClientInitData;
import enstabretagne.cureTatooine.scenarios.ScenarioTatooineInit.AffluenceParMois;
import enstabretagne.engine.EntiteSimulee;
import enstabretagne.engine.Scenario;
import enstabretagne.engine.ScenarioInitData;
import enstabretagne.engine.SimEvent;
import enstabretagne.engine.SimuEngine;

@ToRecord(name = "Scenario")
public class ScenarioTatooineDynamique extends Scenario {
	

	public ScenarioTatooineDynamique(SimuEngine engine, ScenarioInitData init) {
		super(engine, init);
	}

	@Override
	public void creerEntitesSimulees() {
		//vide car on ne crée pas d'entités à priori
	}
	
	int nbClientMax;
	int nbClientMaxMois;
	double tauxCreationClientMois;
	
	@ToRecord(name = "NbClientMaxMois")
	public int getNbClientMaxMois() {
		return nbClientMaxMois;
	}
	
	int nbClientEnCours;
	
	@ToRecord(name = "NbClientEncours")
	public int getNbClientEnCours() {
		return nbClientEnCours;
	}
	
	
	public void setNbClientEnCours(int nbClientEnCours) {
		this.nbClientEnCours = nbClientEnCours;
		
		//on arrête le processus de création de clients si on a atteint le quota du mois (pour simuler de manière représentative 
		//le flux par mois
		if(nbClientEnCours == nbClientMaxMois)
			UnPost(clientCreationProcess);
		//dès que le nombre de client en cours est moindre que le nombre de client max du mois, on relance le process de création d'entité
		if(nbClientEnCours < nbClientMaxMois)
			Post(clientCreationProcess);
	}
	
	protected void creerClientsAleatoire() {
		int nb = nbClientMaxMois-nbClientEnCours;
		int nbAujourdhui = 0;
		
		//nb<0 arrive lorsque le mois actuel présente potentiellement moins de personnes que le mois précédent
		//nbAujourdhui traduit le nombre maximal de clients qu'on peut créer au regard du nombre de personnes présentes en ce moment et du nombre maximal du mois
		if(nb>0) nbAujourdhui =getRandomGenerator().nextInt(0, nb);
		if(nb<0) nb = 0;//nb négatif n'a pas de sens
		Logger.Information(this, "creerClientsAleatoire", "Création de "+nbAujourdhui+ " clients sur "+nb+"possibles");
		for(int i = 0 ;i<nbAujourdhui ; i++) {
			creerClientAleatoire();
		}		
	}
	
	//normalement ici il faut créer un process assez élaboré avec le parcours de soin
	protected void creerClientAleatoire() {
			ClientInitData iniClient = new ClientInitData("C"+nbClientEnCours++) ; 
			Client c = new Client(getEngine(), iniClient);
			c.requestInit();
			
			//On sait que la cure ne dure que 3 semaines donc départ du client dans 3 semaines
			Post(new DepartClient(Now().add(LogicalDuration.ofDay(21)),c));
	}

	//process de création dont on garde une référence pour pouvoir le démarrer et l'arrêter
	CreerClient clientCreationProcess;
	
	@Override
	protected void init() {
		super.init();
		
				
		Logger.Information(this, "scenario.init", getName() + " s'initialise.");
		//A priori dans l'exemple que je donne aucune entité n'est créée dans creerEntitesSimulees
		List<EntiteSimulee> l = recherche(e->{return e instanceof Client ;});
		
		for(EntiteSimulee e: l) {
			e.requestInit();
		}
		
		//on récupère le scénario Tatooine
		ScenarioTatooineInit scenario = (ScenarioTatooineInit) getInit();
		nbClientMax = scenario.capaciteMaxClientInstitut;

		//On prépare les changements d'affluence chaque mois
		for(AffluenceParMois a : scenario.affluenceParMois) {
			Post(new ChangementTauxCreationMois(a.d, a.tauxMax));
		}
		
		//création du processus de création de clients sans forcément le démarrer.
		clientCreationProcess = new CreerClient(Now());

		//création et démarrage du processus d'enregistrement des données
		Post(new RecordProcess(Now()));

		//création de la fin d'affluence 3 semaines avant la fin de la saison
		if(scenario.affluenceParMois.size()>0) {
			AffluenceParMois lastAffluence = scenario.affluenceParMois.get(scenario.affluenceParMois.size()-1);
			Post(new FinDesEntrees3SemainesAvant(lastAffluence.d.add(LogicalDuration.ofDay(7))));
		}
	}

	//
	public class CreerClient extends SimEvent {

		public CreerClient(LogicalDateTime d) {
			super(d);
		}
		
		@Override
		public void process() {
			
			creerClientsAleatoire();
			//arrivage aléatoire
			//l'ennoncé ne donne aucune information à ce sujet. on prend une hypothèse.
			//un arrivage aléatoire par mois mais il peut y en avoir plus.
			int nbJaleaatoireProchainArrivage = getRandomGenerator().nextInt(1, 30);
			this.rescheduleAt(Now().add(LogicalDuration.ofDay(nbJaleaatoireProchainArrivage)));
			Post(this);
		}
		
	}
	
	public class DepartClient extends SimEvent {
		public DepartClient(LogicalDateTime d,Client c) {
			super(d);
		}

		@Override
		public void process() {
			setNbClientEnCours(nbClientEnCours-1);
		}
	}
	
	public class ChangementTauxCreationMois extends SimEvent {

		double tauxPrevu;
		public ChangementTauxCreationMois(LogicalDateTime t,double tauxPrevu) {
			super(t);
			this.tauxPrevu = tauxPrevu;
		}
		@Override
		public void process() {
			tauxCreationClientMois = tauxPrevu;
			nbClientMaxMois = (int) (tauxCreationClientMois*nbClientMax);
			Logger.Information(entitePorteuseEvenement, "Changement Taux", "nouveau taux = "+tauxPrevu+" nbClientMaxMois="+nbClientMaxMois);
			
			clientCreationProcess.rescheduleAt(Now());
			Post(clientCreationProcess);
		}
	}
	
	//post unique de fin des entrées
	public class FinDesEntrees3SemainesAvant extends SimEvent {
		public FinDesEntrees3SemainesAvant(LogicalDateTime d) {
			super(d);
		}

		@Override
		public void process() {
			nbClientMaxMois = 0;
		}
	}
	
	//repost régulier du process d'enregistrement
	public class RecordProcess extends SimEvent {
		
		public RecordProcess(LogicalDateTime d) {
			super(d);
		}

		@Override
		public void process() {
			Logger.Data(entitePorteuseEvenement);
			rescheduleAt(Now().add(LogicalDuration.ofDay(5)));
			entitePorteuseEvenement.Post(this);
		}
	}

}
