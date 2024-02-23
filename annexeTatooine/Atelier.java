package annexeTatooine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
	public ArrayList<Client> fileAttente;
	
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
	    this.fileAttente=new ArrayList<Client>(this.tailleFile);
	     
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

	public ArrayList<Client> getFileAttente() {
		return fileAttente;
	}

	public void setFileAttente(ArrayList<Client> fileAttente) {
		this.fileAttente = fileAttente;
	}
	
	

}
