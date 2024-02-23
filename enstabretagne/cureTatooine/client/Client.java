package enstabretagne.cureTatooine.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import annexeTatooine.Atelier;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.engine.EntiteSimulee;
import enstabretagne.engine.InitData;
import enstabretagne.engine.SimuEngine;

@ToRecord(name="Client")
public class Client extends EntiteSimulee{
	
	List<String> cure;
	Map<String,Integer> pointsParAtelier;

	public Client(SimuEngine engine, InitData ini,List<String> zoneAFaire) {
		super(engine, ini);
		//this.cure=
		this.cure=new ArrayList<String>();
		for(String sc:zoneAFaire) {
			this.cure.add(sc);
		}
		this.pointsParAtelier=new HashMap<>();
		
	}
	
	@ToRecord(name="Points de soins")
	double getPointsTotalEnCours() {
		return 0;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	protected void init() {
		super.init();
		System.out.println("client initialisé");
		//Logger.Information(this, "init", "Initialisation en cours");
	}

	public List<String> getCure() {
		return cure;
	}

	public void setCure(List<String> cure) {
		this.cure = cure;
	}

	public Map<String, Integer> getPointsParAtelier() {
		return pointsParAtelier;
	}

	public void setPointsParAtelier(Map<String, Integer> pointsParAtelier) {
		this.pointsParAtelier = pointsParAtelier;
	}

	
	
	
}
