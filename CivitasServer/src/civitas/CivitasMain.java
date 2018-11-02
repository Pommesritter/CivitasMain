package civitas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import civitas.dateisystem.Dateisystem;
import civitas.world.Welt;

public class CivitasMain {
	private static Dateisystem dateisystem = null;
	private static final String dateipfad = 
			System.getProperty("user.home") + "/AppData/CivitasServer";
	public static final String dateiendung = "ser";
	/**
	 * Liste der erlaubten Hauptargumente
	 * Jedes erlaubte Argument muss hier eingetragen sein.
	 * Wenn nicht eingetragene Argumente verwendet werden, 
	 * bricht das Programm mit Code 0 ab.
	 */
	private final static String[] erlaubteArgumente = 
		{ "dateisystem", "test" };
	//und so weiter...
	private final static String[] erlaubteArgumenteDateisystem = 
		{ "init", "check" };

	private final static String[] erlaubteArgumenteTest = 
		{ " -+- ", "welt" };
	//TODO - Weitere Argumente...
	
	/**
	 * Debugmodus an oder aus?
	 */
	public static boolean debug = true;
	
		
	/**
	 * Hauptmethode des CivitasServer
	 * @param args Argumente, die der Benutzer eingibt (Konsole).
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("*****************" +
						   "  Civitas Server - Hauptpaket  " +
						   "*****************");
		
		if(args.length==0) {
			System.out.println("Bitte Argumente angeben.");
			erlaubteArgsAusgeben();
			System.exit(0);
		}
		
/////////////////////////////////////////////////////////////////////////////
//begin////////////////HAUPTSWITCH///////////////////////////////////////////
//begin//////////////////CivitasServer.jar dateisystem [init, check]/////////
//begin//////////////////CivitasServer.jar test [welt]///////////////////////
//means//////////////////////////////////////////////////////////////////////
			switch(args[0]) {
			
			
	//begin////////////BEFEHLSPARAMETER "dateisystem"////////////////////////
				case "dateisystem":
					
					if(!(args.length == 1)) {
					/////////////////////////////////////////////////////////
						switch(args[1]) {
						//////////////////dateisystem init///////////////////
							case "init":
								dateisystemInit();
							return;
							/////////////dateisystem check///////////////////		
							case "check":
								checkDateisystem();
								
							return;
							///////////////usw/usw/usw/usw///////////////////	
							case "usw.":
								
							return;
						//////////////////////////////////////////////////////
						} } else erlaubteArgsDateisystemAusgeben();
					
				return;
				//////////////////////////////////////////////////////////////
				
	//end///////////////ENDE BEFEHLSPARAMETER DATEISYSTEM/////////////////////
				
				
	//begin////////////BEFEHLSPARAMETER "test"////////////////////////////////
				case "test":
					if(args.length > 1) {
						
					//begin//////////////SWITCH ARGS[1]//////////////////////
						switch(args[1]) {
							//////////////////////////////////////////////////
							case "welt":
								test(args);
							return;
							//////////////////////////////////////////////////
							case "": erlaubteArgsTestAusgeben() ;
						} 
					//end//////////////ENDE SWITCH ARGS[1]////////////////////
						} else test();
					return;      
	//end///////////////ENDE BEFEHLSPARAMETER TEST////////////////////////////
				
			}
			
//end/////////////////////////////////////////////////////////////////////////
//end////////////ENDE HAUPTSWITCH CIVITASSERVER///////////////////////////////
//end/////////////////////////////////////////////////////////////////////////
		System.out.println("Bitte einen gültigen Befehl eingeben: ");
		erlaubteArgsAusgeben();
		exit(0);
		
	}
	
	//end main
	
	

	
	
	
	/**
	 * Wenn das Verzeichnis des Dateisystems geändert 
	 * oder die Datei gelöscht wurde,
	 * aktualisiert diese Methode das Dateisystem.
	 */
		private static void dateisystemInit() {
			dateisystemAuslesen();
			if(dateisystem==null) {
				dateisystem = new Dateisystem();
			debug("Kein Dateisystem gespeichert, erstelle neues...");
				dateisystemSpeichern();
			}
			System.out.println("Dateisystem wurde Initialisiert.");
			exit(0);
		}
		/**
		 * Liest das aktuelle Dateisystem aus dem 
		 * angegebenen Pfad (Konstante Dateipfad)
		 */
		private static void dateisystemAuslesen() {
			FileInputStream filestream  = null;
			ObjectInputStream objectinput = null;
			String dateisystempfad = dateipfad + "/bin/dateisystem."+ dateiendung;
			
			try {
				File dateisystemfile = 
				new File(dateisystempfad);
			debug("Dateisystem wird geladen aus " + dateisystempfad);
				dateisystemfile.createNewFile();
				
				filestream = new FileInputStream(dateisystemfile);
				objectinput = new ObjectInputStream(filestream);
				dateisystem = (Dateisystem) objectinput.readObject();
				
				filestream.close();
				objectinput.close();
				if(dateisystem == null) {
			debug(
						"Dateisystem konnte nicht aus vorhandenen Dateien "
						+ "ausgelesen werden.");
		exit();
				}
			debug("Vorhandenes Dateisystem wurde ausgelesen.");
				} catch (FileNotFoundException e1) {
			debug(
						"Es konnte kein Dateisystem gefunden werden.");
			debug(
						"Bitte mit 'CivitasServer dateisystem init' initialisieren.");
		exit();
					} catch (IOException e) { 
			debug("Eine IOException ist aufgetreten. Es wurde nichts" +
					"initialisiert.");
		exit();
						} catch (ClassNotFoundException e) { 
			debug("Eine ClassNotFoundException ist aufgetreten. Es " +
					"wurde nichtsinitialisiert.");
							e.printStackTrace();
		exit();
							}
		}
		
		private static void checkDateisystem() {
			
		}
		
		/**
		 * Überprüft, ob ein Dateisystem im Programm registriert ist.
		 */
		private static boolean hasDateisystem() {
			if(dateisystem == null) {
			debug("Es ist scheinbar noch kein "
						+ "Dateisystem initialisiert worden.");
				return false;
			}
			return true;
		}
		private static void test() {
				System.out.println("Test!");
				exit(0);
		}

		

	
	/**
	 * Speichert das geladene Dateisystem im konstanten Pfad ab.
	 */
	private static void dateisystemSpeichern() {
		FileOutputStream filestream  = 
				null;
		ObjectOutputStream objectoutput = 
				null;
		File dateisystemfile = new File(
				dateipfad + 
				"/bin/dateisystem"+ "."+ dateiendung);
		
		debug("Dateisystem gespeichert unter " + dateisystemfile);
		////////////////////BEGIN IO-try
		try {
			dateisystemfile.getParentFile().mkdirs();
			dateisystemfile.createNewFile();
			filestream = new FileOutputStream( dateisystemfile );
			objectoutput = new ObjectOutputStream(filestream);
			objectoutput.writeObject(dateisystem);
			filestream.close();
			objectoutput.close();
			
			debug("Vorhandenes Dateisystem wurde ausgelesen.");
			////////////////////BEGIN Catch-Blöcke
				} catch (FileNotFoundException e1) {
					debug("Speichern fehlgeschlagen: "
							+ "Es konnte kein Dateisystem gefunden werden.");
					if(debug)
						e1.printStackTrace();
					return;
					} catch (IOException e) { 
						e.printStackTrace();
						debug("IOException für " + dateisystemfile);
						
					}
			////////////////////END Catch
		////////////////////END IO-try
		if(dateisystem == null) {
			debug("Im Programm konnte kein Dateisystem gefunden werden.");
		}
		exit();
	}
	
	public static String getHauptverzeichnis() {
		return dateipfad;
	}

	
	private static void test(String[] args) {
		
		//TODO: Argumente switchen
		if(args.length == 2) {
			if(args[1].equals("welt") ) {
				debug("Erstelle und speichere Welt mit dem Testindex 9999" 
						+  "...");
				Welt neueWelt = new Welt(9999);
				
				if(hasDateisystem()) {
					dateisystem.speichern(neueWelt);
					exit(0);
				} else {
				exit("Das Dateisystem ist noch nicht "
						+ "initialisiert. ");
				}
				
				
				
			}
			else {
				erlaubteArgsTestAusgeben();
				exit(0);
			}
			return;
		}
		
	}
	public static void debug(String nachricht) {
		if(debug == true) {
			System.out.println("[DEBUG] " + nachricht);
		}
		else {
			System.out.println("+");
			System.out.println("-");
			System.out.println("+");
		}
	}
	
	private static void exit() {
		System.exit(0);
	}
	private static void exit(int status) {
		System.exit(status);
	}
	private static void exit(String meldung) {
		System.out.println(meldung);
		System.exit(0);
	}
	
	
	
	/**
	 * Erlaubte Argumente für "CivitasServer [...]" ausgeben 
	 * und Programm abbrechen
	 */
	private static void erlaubteArgsAusgeben() {
		System.out.println("Erlaubte Argumente: ");
		for(String s : erlaubteArgumente) {
			System.out.println("* " + s);
		}
		exit(0);
	}
	/**
	 * Erlaubte Argumente für "CivitasServer dateisystem [...] ausgeben 
	 * und Programm abbrechen
	 */
	private static void erlaubteArgsDateisystemAusgeben() {
		System.out.println("Erlaubte Argumente für das Civitas-Dateisystem: ");
		for(String s : erlaubteArgumenteDateisystem) {
			System.out.println("* " + s);
		}
		exit(0);
	}
	/**
	 * Erlaubte Argumente für "CivitasServer test [...] ausgeben 
	 * und Programm abbrechen
	 */
	private static void erlaubteArgsTestAusgeben() {
		System.out.println("Erlaubte Argumente: ");
		for(String s : erlaubteArgumenteTest) {
			System.out.println("* " + s);
		}
		exit(0);
	}
}
