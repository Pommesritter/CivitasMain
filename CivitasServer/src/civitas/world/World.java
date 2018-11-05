package civitas.world;

import civitas.CivitasMain;
import civitas.dateisystem.CivitasSaveable;
import civitas.dateisystem.SaveableList;

public class World implements CivitasSaveable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2154569878018521490L;
	
	private int index = 0;
	private String name;
	
	/**
	 * @param name : Name der Welt
	 * @param listenindex : Index in der Weltenliste
	 */
	public World(String name, int listenindex) {
		if(listenindex < 0) {
			debug("Der angegebene Index scheint negativ zu sein.");
			return;
		}
		this.name = name;
		this.index = listenindex;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String getSpeicherortRelativ() {
		return SaveableList.WORLD.getFolderPath();
	}

	@Override
	public int getListenposition() {
		return index;
	}
	
	public void debug(String msg) {
		msg = "[Welt]" + msg;
		CivitasMain.debug(0, msg);
	}

	@Override
	public void setListenposition(int i) {
		index = i;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
