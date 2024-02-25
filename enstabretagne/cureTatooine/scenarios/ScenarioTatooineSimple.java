package enstabretagne.cureTatooine.scenarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import annexeTatooine.Atelier;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.math.MoreRandom;
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
import java.util.Date;
@ToRecord(name = "Scenario")
public class ScenarioTatooineSimple extends Scenario {
	
	int nbClientMax;
	int nbClientMaxMois;
	int nbClientEnCours;
	List<Atelier> zones;
	
	List<Integer> nbAtelier;//gérer les proportions
	List<Double> proportion;//gérer les proportions
	
	public Atelier getAtelierByName(String nomAtelier) {
		
		for(Atelier a: this.zones) {
			if(a.getNom().compareTo("nomAtelier")==0) {
				return a;	
			}
		}
		return null;
	}

	public ScenarioTatooineSimple(SimuEngine engine, ScenarioInitData init) {
		super(engine, init);
		
		fixerProportion();
		
		creerZones();
		
	}
	
	
	public void fixerProportion() {
		
		this.nbAtelier= new ArrayList<Integer>();
		this.proportion= new ArrayList<Double>();
		
		this.nbAtelier.add(3);
		this.nbAtelier.add(4);
		this.nbAtelier.add(5);
		this.nbAtelier.add(6);
		
		this.proportion.add(0.2);
		this.proportion.add(0.35);
		this.proportion.add(0.3);
		this.proportion.add(0.15);
		
	}

	@Override
	public void creerEntitesSimulees() {
		
		System.out.println("début ScenarioTatooineSimple:creerEntitesSimulees");
		
		
		ScenarioTatooineInit scenario = (ScenarioTatooineInit) getInit();
		nbClientMax = scenario.capaciteMaxClientInstitut;

		//On prépare les changements d'affluence chaque mois
		for(AffluenceParMois a : scenario.affluenceParMois) {
			Post(new ChangementTauxCreationMois(a.d,a.tauxMax));
			
			
			for(int x=0;x<this.nbAtelier.size(); x++) {
				
				double fraction=this.proportion.get(x);
				//System.out.println("proportionFraction "+(x+1));
				//System.out.println("fraction ----- "+fraction);
				int fractionCuriste=(int)(a.tauxMax*nbClientMax*fraction);//je prend la fraction
				//System.out.println("nombre cherché ----------"+fractionCuriste); le calcul avec virgule peut faire perdre un cient peut etre meme deux
				int nombreAtelierFraction=this.nbAtelier.get(x);
				
				
				//je genère le nombre d'ateliers approprié
				MoreRandom mr= new MoreRandom();
				
				for(int i=0;i<(fractionCuriste);i++) {
					
					ArrayList<String> zonesAfaire=new ArrayList();//à remplir et sera effecté au client
					ArrayList<String> inteAzone=new ArrayList();
					for(Atelier p:this.zones) {
						inteAzone.add(p.getNom());
						
					}
					
					for(int k=0;k<nombreAtelierFraction;k++) {
						int randomIndex = mr.nextInt(inteAzone.size());
						zonesAfaire.add(inteAzone.get(randomIndex));
						inteAzone.remove(randomIndex);
						
					}
					//fin genèration d'ateliers approprié
					
					int r=mr.nextInt(8)+1;// un client peut arriver dans les 9 premier jours d'un mois
					//System.out.println("Cure "+zonesAfaire);
					Post(new CreerClient(a.d.add(LogicalDuration.ofDay(r)),zonesAfaire));

					
					
				}
				
				
			}
			
			
		}
		
		System.out.println("fin ScenarioTatooineSimple:creerEntitesSimulees");
	}
	

	
	@ToRecord(name = "NbClientMaxMois")
	public int getNbClientMaxMois() {
		return nbClientMaxMois;
	}
	
	
	@ToRecord(name = "NbClientEncours")
	public int getNbClientEnCours() {
		return nbClientEnCours;
	}
	
	
	public void setNbClientEnCours(int nbClientEnCours) {
		System.out.println("un client a terminé sa cure");
		this.nbClientEnCours = nbClientEnCours;
	}
	
	
	//normalement ici il faut créer un process assez élaboré avec le parcours de soin
	protected void creerClientAleatoire(ArrayList<String> zonesAfaire) {
		
			ClientInitData iniClient = new ClientInitData("C"+nbClientEnCours++) ; 
			Client c = new Client(getEngine(), iniClient,zonesAfaire);
			c.requestInit();
			
			//Post(new StartCures(localdatetime,c));//cette ligne servira à démarer la réalisation des atelier
			
			//On sait que la cure ne dure que 3 semaines donc départ du client dans 3 semaines
			Post(new DepartClient(Now().add(LogicalDuration.ofDay(21)),c));
	}

	@Override
	protected void init() {
		super.init();
		
		System.out.println("scénario initialisé");		
		//Logger.Information(this, "scenario.init", getName() + " s'initialise.");
		List<EntiteSimulee> l = recherche(e->{return e instanceof Client ;});
		
		for(EntiteSimulee e: l) {
			e.requestInit();
		}
		

		//création et démarrage du processus d'enregistrement des données
		Post(new RecordProcess(Now()));

	}

	//
	public class CreerClient extends SimEvent {
		
		ArrayList<String> zonesAfaire;

		public CreerClient(LogicalDateTime d,ArrayList<String> zonesAfaire) {
			super(d);
			this.zonesAfaire=new ArrayList<String>();
			for(String s:zonesAfaire) {
				this.zonesAfaire.add(s);
			}
		}
		
		@Override
		public void process() {
			
			creerClientAleatoire(this.zonesAfaire);
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
	
	
	public class StartCures extends SimEvent {//evenemnt qui démare la réalisation des ateliers
		
		public Client curiste;

		public StartCures(LogicalDateTime d,Client curiste) {
			super(d);
			// TODO Auto-generated constructor stub
			this.curiste=curiste;
		}

		@Override
		public void process() {
			// TODO Auto-generated method stub
			
			for(String natelier:this.curiste.getCure()) {
				
				//faut ajouter check if atelier ouvert
				
				Atelier refat=getAtelierByName(natelier);
				
				if(this.curiste.getPositionCourante()!="detente") {
					//faut jouter logique deplacement qlqe part: deplcamenet avant verification disponibilite
					//afin deviter situation ou deux dans des ateliers differents verifie et apres les deux arrive en meme temps donc un seul entre
					int distance=refat.getDistances().get(this.curiste.getPositionCourante());
					//ajouter logique d'attente relative au déplacement
					
				}

				if(curiste.getPointsParAtelier().get("natelier")!=0) {//un atelier deja fait cad ses points>0 ne doit pas etre refais meme si ces points ne sont pas au max
					continue;
				}
				
				boolean v=refat.nouveauClient(curiste);
				if(v) {//v=true => client a pu entrr en atelier ou en file attente
					break;
				}
				
			}
			
			
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
			nbClientMaxMois = (int) (tauxPrevu*nbClientMax);
			Logger.Information(null, "Changement Taux", "nouveau taux = "+tauxPrevu+"/ nouveau nbClientMaxMois="+nbClientMaxMois);
			
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
	
	
	public void creerZones() {
		//créer les zones
		
				System.out.println("début création zones");
				Atelier zone1=new Atelier("Zone des terres chaudes", "terres","planifié", new SimpleDateFormat("07:15"), new SimpleDateFormat("14:00"), 6, LogicalDuration.ofMinutes(20), 20, LogicalDuration.ofDay(61), 10, LogicalDuration.ofDay(3), "organisé", 10);
				Atelier zone2=new Atelier("Zone des jets filiformes", "filiforme","libre", new SimpleDateFormat("10:00"), new SimpleDateFormat("13:00"), 4, LogicalDuration.ofMinutes(5), 30, LogicalDuration.ofDay(28), 4, LogicalDuration.ofDay(2), "non organisé", 10);
				Atelier zone3=new Atelier("Zone des étuves", "étuve","planifié", new SimpleDateFormat("07:15"), new SimpleDateFormat("14:00"), 6, LogicalDuration.ofMinutes(15), 15, LogicalDuration.ofDay(21), 5, LogicalDuration.ofDay(3), "organisé", 6);
				Atelier zone4=new Atelier("Zone Bains à jets", "bain","libre", new SimpleDateFormat("07:15"), new SimpleDateFormat("14:00"), 9, LogicalDuration.ofMinutes(20), 10, LogicalDuration.ofDay(35), 4, LogicalDuration.ofDay(2), "organisé", 15);
				Atelier zone5=new Atelier("Zone des douches", "douche","libre", new SimpleDateFormat("07:15"), new SimpleDateFormat("14:00"), 8, LogicalDuration.ofMinutes(10), 10, LogicalDuration.ofDay(49), 2, LogicalDuration.ofDay(2), "non organisé", 8);
				Atelier zone6=new Atelier("Zone des soins du visage", "visage","libre", new SimpleDateFormat("07:15"), new SimpleDateFormat("14:00"), 8, LogicalDuration.ofMinutes(10), 5, LogicalDuration.ofDay(365), 40, LogicalDuration.ofDay(1), "non organisé", 5);
				
				this.zones=new ArrayList<Atelier>();
				this.zones.add(zone1);
				this.zones.add(zone2);
				this.zones.add(zone3);
				this.zones.add(zone4);
				this.zones.add(zone5);
				this.zones.add(zone6);
				//
				System.out.println("fin création zones");
	}

}
