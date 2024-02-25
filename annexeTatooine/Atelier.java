package annexeTatooine;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.cureTatooine.client.Client;

public class Atelier {
	
	String nom;
	String typeSoin;
	SimpleDateFormat ouverture;
	SimpleDateFormat fermeture;
	int NombreAtelier;
	LogicalDuration dureeAtelier;
	int efficacite;
	LogicalDuration freqDefaillance;
	double stdDefaillance;
	LogicalDuration tempsRemiseMarche;
	String typeAttente;
	int tailleFile;
	int NombreAtelierOccupe;
	String typeFreq;
	public Queue<Client> fileAttente;
	Map<String,Integer> distances;
	
	public Atelier(String nom,String typeSoin,String typeFreq,SimpleDateFormat ouverture,SimpleDateFormat fermeture,int NombreAtelier,LogicalDuration dureeAtelier,int efficacite,LogicalDuration freqDefaillance,double stdDefaillance,LogicalDuration tempsRemiseMarche,String typeAttente,int tailleFile ) {
		this.nom = nom;
	    this.typeSoin = typeSoin;
	    this.typeFreq=typeFreq;
	    this.ouverture = ouverture;
	    this.fermeture = fermeture;
	    this.NombreAtelier = NombreAtelier;
	    this.dureeAtelier = dureeAtelier;
	    this.efficacite = efficacite;
	    this.freqDefaillance = freqDefaillance;
	    this.stdDefaillance = stdDefaillance;
	    this.tempsRemiseMarche = tempsRemiseMarche;
	    this.typeAttente = typeAttente;
	    this.tailleFile = tailleFile;
	    this.NombreAtelierOccupe=NombreAtelier;
	    this.fileAttente = new LinkedList<Client>();
	    this.distances=new HashMap<String,Integer>();
	    
	    this.fixDistances(this.nom);
	    
	     
	}
	
	public boolean nouveauClient(Client curiste) {
		
		if(this.NombreAtelier>this.NombreAtelierOccupe) {
			this.NombreAtelierOccupe=this.NombreAtelierOccupe+1;
			//client utilise atelier
			//Post(new AtelierEntry());//doit genereer une sortie apres le temps de l'atelier//atelierEntry event doit augmenter les points
			
			return true;
		}
		else {
			if(this.fileAttente.size()>=tailleFile) {//le cas supérieur ne doit pas etre possible, il est inclut juste pour ecrire else
				//rejeter client car pas de place et file pleine//doit verifier s'il a d'autre atelier a faire
				return false;
				
			}
			else {
				this.fileAttente.add(curiste);
				//client rejoint la file d'attente//attend son tour
				return true;
			}
			
		}
				
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTypeSoin() {
		return typeSoin;
	}

	public void setTypeSoin(String typeSoin) {
		this.typeSoin = typeSoin;
	}

	public SimpleDateFormat getOuverture() {
		return ouverture;
	}

	public void setOuverture(SimpleDateFormat ouverture) {
		this.ouverture = ouverture;
	}

	public SimpleDateFormat getFermeture() {
		return fermeture;
	}

	public void setFermeture(SimpleDateFormat fermeture) {
		this.fermeture = fermeture;
	}

	public int getNombreAtelier() {
		return NombreAtelier;
	}

	public void setNombreAtelier(int nombreAtelier) {
		NombreAtelier = nombreAtelier;
	}

	public LogicalDuration getDureeAtelier() {
		return dureeAtelier;
	}

	public void setDureeAtelier(LogicalDuration dureeAtelier) {
		this.dureeAtelier = dureeAtelier;
	}

	public int getEfficacite() {
		return efficacite;
	}

	public void setEfficacite(int efficacite) {
		this.efficacite = efficacite;
	}

	public LogicalDuration getFreqDefaillance() {
		return freqDefaillance;
	}

	public void setFreqDefaillance(LogicalDuration freqDefaillance) {
		this.freqDefaillance = freqDefaillance;
	}

	public double getStdDefaillance() {
		return stdDefaillance;
	}

	public void setStdDefaillance(double stdDefaillance) {
		this.stdDefaillance = stdDefaillance;
	}

	public LogicalDuration getTempsRemiseMarche() {
		return tempsRemiseMarche;
	}

	public void setTempsRemiseMarche(LogicalDuration tempsRemiseMarche) {
		this.tempsRemiseMarche = tempsRemiseMarche;
	}

	public String getTypeAttente() {
		return typeAttente;
	}

	public void setTypeAttente(String typeAttente) {
		this.typeAttente = typeAttente;
	}

	public int getTailleFile() {
		return tailleFile;
	}

	public void setTailleFile(int tailleFile) {
		this.tailleFile = tailleFile;
	}

	public int getNombreAtelierOccupe() {
		return NombreAtelierOccupe;
	}

	public void setNombreAtelierOccupe(int nombreAtelierOccupe) {
		NombreAtelierOccupe = nombreAtelierOccupe;
	}

	public String getTypeFreq() {
		return typeFreq;
	}

	public void setTypeFreq(String typeFreq) {
		this.typeFreq = typeFreq;
	}

	public Queue<Client> getFileAttente() {
		return fileAttente;
	}

	public void setFileAttente(Queue<Client> fileAttente) {
		this.fileAttente = fileAttente;
	}
	
	public void fixDistances(String nomZone) {
		
		switch(nomZone) {
	    case "Zone des jets filiformes":
	    	this.distances.put("Zone des douches", 1);
	    	this.distances.put("Zone Bains à jets", 2);
	    	this.distances.put("Zone des soins du visage", 4);
	    	this.distances.put("Zone des étuves", 1);
	    	this.distances.put("Zone des terres chaudes", 3);
	    	break;
	    case "Zone des douches":
	    	this.distances.put("Zone des jets filiformes", 1);
	    	this.distances.put("Zone Bains à jets", 1);
	    	this.distances.put("Zone des soins du visage", 2);
	    	this.distances.put("Zone des étuves", 2);
	    	this.distances.put("Zone des terres chaudes", 4);
	    	break;
	    case "Zone Bains à jets":
	    	this.distances.put("Zone des jets filiformes", 2);
	    	this.distances.put("Zone des douches", 1);
	    	this.distances.put("Zone des soins du visage", 1);
	    	this.distances.put("Zone des étuves", 3);
	    	this.distances.put("Zone des terres chaudes", 3);
	    	break;
	    case "Zone des soins du visage":
	    	this.distances.put("Zone des jets filiformes", 4);
	    	this.distances.put("Zone des douches", 2);
	    	this.distances.put("Zone Bains à jets", 1);
	    	this.distances.put("Zone des étuves", 4);
	    	this.distances.put("Zone des terres chaudes", 2);
	    	break;
	    case "Zone des étuves":
	    	this.distances.put("Zone des jets filiformes", 1);
	    	this.distances.put("Zone des douches", 2);
	    	this.distances.put("Zone Bains à jets", 3);
	    	this.distances.put("Zone des soins du visage", 4);
	    	this.distances.put("Zone des terres chaudes", 2);
	    	break;
	    case "Zone des terres chaudes":
	    	this.distances.put("Zone des jets filiformes", 3);
	    	this.distances.put("Zone des douches", 4);
	    	this.distances.put("Zone Bains à jets", 3);
	    	this.distances.put("Zone des soins du visage", 2);
	    	this.distances.put("Zone des étuves", 2);
	    	break;

	    }
		this.distances.put("detente", 0);
	}
	
	

}
