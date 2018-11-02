package civitas.world;

import civitas.CivitasMain;
import civitas.dateisystem.CivitasSaveable;

public class Welt implements CivitasSaveable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2154569878018521490L;
	
	private int index = 0;
	private String name = "welt";
	public Welt(int listenindex) {
		if(listenindex < 0) {
			debug("Der angegebene Index scheint negativ zu sein.");
			return;
		}
		this.index = listenindex;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String getSpeicherortRelativ() {
		return "spieldateien";
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
}
