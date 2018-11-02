package civitas.dateisystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import civitas.CivitasMain;

/**
 * @author herbert
 * @Dateien Akzeptiert CivitasSaveables.
 * Das Dateisystem, das f�r die Verwaltung 
 * der Dateihierarchie verantwortlich ist 
 * 
 */
public class Dateisystem  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1922961680844147228L;
	
	private String stammverzeichnis = CivitasMain.getHauptverzeichnis();
	/**
	 * Diese Klasse ver�ndert nur die Dateien in den durch den Konstruktor angegebenen Ordnern. 
	 * Diese Ordner sind dabei Unterordner des Stammverzeichnisses.
	 **/
	final String hauptOrdnerName = "bin";
	
	/**
	 * Hier werden die Pfade der Ordner angegeben, die dieses Dateisystem erfasst
	 */
	
	private final String absoluterPfadHauptOrdner = chain(stammverzeichnis, hauptOrdnerName);
	
	public Dateisystem (){
		stammverzeichnis = CivitasMain.getHauptverzeichnis();
	}
	
	public URL verzeichnisURLabrufen(String relativerDateipfad) {
		String angeforderterDateipfad = stammverzeichnis + "/" + relativerDateipfad;
		File angeforderteDatei =  new File(angeforderterDateipfad);
		URL url = null;
		
		try {
			url = angeforderteDatei.toURI().toURL();
		} catch (MalformedURLException e) {
		}
		if(url == null) {
			//
			debug("Die Datei " + angeforderterDateipfad + " konnte nicht ausgelesen werden.");
		}
		return url;
		
	}
		/**
		 * Methode, um ein Objekt nach den Regeln des Datensystems zu speichern.
		 * Der Hauptordner ist in der Hauptklasse angegeben. Die Unterordner sind in den Saveable-Klassen angegeben.
		 * @param saveable : jedes Objekt, das das Saveable-Interface implementiert.
		 * 
		 */
		public void speichern(CivitasSaveable saveable) {
			
			//Zieldatei ermitteln
			File zieldatei = new File( getDateipfad(saveable) );
			//
			debug(saveable.getName() + " wird gespeichert unter " + zieldatei + "...");
			
			/*
			 * Serialisieren bzw. speichern
			 */
			FileOutputStream fileout  = null;
			ObjectOutputStream objectout = null;
			try {
				zieldatei.getParentFile().mkdirs();
				zieldatei.createNewFile();
				fileout = new FileOutputStream(zieldatei);
				objectout = new ObjectOutputStream(fileout);
				objectout.writeObject(saveable);
				
				fileout.close();
				objectout.close();
				
							} catch (FileNotFoundException e1) {
										if(CivitasMain.debug)
											e1.printStackTrace();
										//
										debug("Speichern fehlgeschlagen: Es konnte keine entsprechende Datei erzeugt werden.");
										return;
							} catch (IOException e) { 
								//
								debug("IOException f�r " + zieldatei);
								e.printStackTrace();
								return;
							}
			System.out.println("Das scheint funktioniert zu haben.");
		}
		
		public void l�schen(CivitasSaveable saveable) {
			
		}
	public String getArbeitsverzeichnis() {
		return absoluterPfadHauptOrdner;
	}
	private String getDateipfad(CivitasSaveable saveable) {
		String zielspeicherort = chain(stammverzeichnis, saveable.getSpeicherortRelativ());
		String saveableDateiname = chain(zielspeicherort, saveable.getName() + "." + CivitasMain.dateityp);
		return saveableDateiname;
	}
	private void debug(String msg) {
		//
		CivitasMain.debug(0, "[Dateisystem] " + msg);
	}
	
	public static String chain(String dateipfad, String relativerNameDatei) {
		String chain = dateipfad + "/" + relativerNameDatei;
		return chain;
	}
}
