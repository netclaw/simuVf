package enstabretagne.cureTatooine.client;

import java.util.List;

import annexeTatooine.Atelier;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.engine.EntiteSimulee;
import enstabretagne.engine.InitData;
import enstabretagne.engine.SimuEngine;

@ToRecord(name="Client")
public class Client extends EntiteSimulee{
	
	List<Atelier> cure;
	List<Integer> points;

	public Client(SimuEngine engine, InitData ini/*,List<String> cure*/) {
		super(engine, ini);
		//this.cure=
		
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
	
	
}
