package civitas.dateisystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import civitas.CivitasMain;

/**
 * @author herbert
 * @Dateien Akzeptiert CivitasSaveables.
 * Das Dateisystem, das für die Verwaltung 
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
	 * Diese Klasse verändert nur die Dateien in den durch den Konstruktor angegebenen Ordnern. 
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
			File zieldatei = new File( chain(saveable.getSpeicherortRelativ(), saveable.getName() + CivitasMain.dateityp ));
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
								debug("IOException für " + zieldatei);
								e.printStackTrace();
								return;
							}
			System.out.println("Das scheint funktioniert zu haben.");
		}
		
		public CivitasSaveable lesen(CivitasSaveableObject obj, String name) throws ClassNotFoundException, IOException {
		
			CivitasSaveable saveable = null;
			
			//Switchhhhhhh it!
			switch(obj) {
				case WORLD : {
						String filename = name + CivitasMain.dateityp;
						File file = new File( chain (stammverzeichnis  , obj.getFolderPath()) + "/" + filename );
						//Stream the file system out of the File
						FileInputStream fs = new FileInputStream(file);
						ObjectInputStream oi = new ObjectInputStream(fs);
						saveable = (CivitasSaveable) oi.readObject();
						fs.close();
						oi.close();
					}
				
				
			}
			
			return saveable;
		}
		
		public void löschen(CivitasSaveable saveable) {
			
		}
	public String getArbeitsverzeichnis() {
		return absoluterPfadHauptOrdner;
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
