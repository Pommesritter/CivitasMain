package civitas.dateisystem;

import java.io.Serializable;

/**
 * @author herbert
 *	CivitasSaveable repr�sentiert alle Klassen, 
 *  die im Dateisystem des Programms abgelegt werden k�nnen.
 */
public interface CivitasSaveable extends Serializable {
	/*
	 *  Achtung! Der relative Speicherort ist der Ort 
	 *  _innerhalb des Hauptordners_, 
	 *  in dem Objekte der jeweiligen Saveable-Klasse 
	 *  gespeichert werden sollen.
	 *  Beispielhafte Werte: 
	 *  -"spieldateien", 
	 *  -"spieldateien/besondere Dateien"
	 */
	abstract String getSpeicherortRelativ();
	/*
	 * Innerhalb dieses Ordners wird dann ein weiterer Ordner erzeugt, 
	 * der den Klassennamen tr�gt.
	 * wenn getSpeicherortRelativ eine leere Zeichenkette 
	 * zur�ckgibt, dann wird im Hauptordner ein Ordner mit dem 
	 * Namen erzeugt, der in der Klasse angegeben ist.
	 */
	abstract String getName();
	abstract int getListenposition();
	abstract void setListenposition(int i);
}
