package enstabretagne.engine;

/**données d'initialisation minimale d'une entité
elle a un nom...*/

public abstract class InitData {
	public String name;
	public InitData(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
