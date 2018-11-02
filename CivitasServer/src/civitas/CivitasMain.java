package civitas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;

import civitas.dateisystem.Dateisystem;
import civitas.world.Welt;

public class CivitasMain {
	
	private static URI dateiuri;
	
	public static final String dateisystemname = "dateisystem";
	public static final String dateityp = "ser";
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
	public static void main(String[] args) throws Exception {
		
		dateiuri = CivitasMain.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		File jarfile = new File(dateiuri);
		dateiuri = jarfile.getParentFile().toURI();
		final int debuglayer = 0;
		System.out.println("*****************  Civitas Server - Hauptpaket  *****************");
		
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
								initFileSystem(debuglayer);
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
								test(args, debuglayer);
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
		exitError(0);
		
	}
	
	//end main
	
	
/*
 * IMPORTANT:
 * ONLY USE exit() IN MAIN METHODS; 
 * ONLY USE exit(error code) IN SUBMETHODS
 */
	
	
	
	/**
	 * Wenn das Verzeichnis des Dateisystems geändert 
	 * oder die Datei gelöscht wurde,
	 * aktualisiert diese Methode das Dateisystem.
	 */
		private static void initFileSystem(int debuglayer) {
			debugExec(debuglayer, "exec initFilySystem...");
			try {
				debug(debuglayer, "try...");
				readFileSystemFromPath(debuglayer + 1);
				debug(debuglayer, "success!");
			} catch (IOException e) {
				debug(debuglayer, "catch...");
				debug(debuglayer, "Die Datei scheint leer zu sein. ");
				debug(debuglayer, "try saveFileSystem...");
				saveFileSystem(debuglayer + 1);
				debug(debuglayer, "Dateisystem initialisiert.");
			}
		
			System.out.println("Dateisystem wurde Initialisiert.");
			exit();
		}
		
		
		/**
		 * Method for the command "CivitasServer test [...]"
		 * 
		 * @param args
		 */
		private static void test(String[] args, int debuglayer) {
			debugExec(debuglayer, "test...");
			//TODO: Argumente switchen
			if(args.length == 2) {
				if(args[1].equals("welt") ) {
					debug(debuglayer, "Erstelle und speichere Welt mit dem Testindex 9999" 
							+  "...");
					Welt neueWelt = new Welt(9999);
					getFileSystem(debuglayer + 1).speichern(neueWelt);
					
					
					
				}
				else {
					erlaubteArgsTestAusgeben();
					exitError(0);
				}
				return;
			}
			exit();
			
		}
		
		/**
		 * Liest das aktuelle Dateisystem 
		 * Der Pfad ist in dieser Klasse angegeben (Konstante "dateiuri")
		 */
		private static Dateisystem readFileSystemFromPath(int debuglayer) throws IOException {
			debugExec(debuglayer, "readFileSystemFromPath...");
			String dateisystempfad = getSubfolderPathOf("dateisystem." + dateityp);
			
			try {
					File datasystemfile = new File(dateisystempfad);
					debug(debuglayer, "Dateisystem wird gelesen aus Datei " + datasystemfile);
					
					//In case the file does not exist, it will be created and standartized now.
					if(!datasystemfile.exists()) {
						debug(debuglayer, "Diese Datei existiert nicht.");
						debug(debuglayer, "Erstelle neu...");
						//Creating new file
						datasystemfile.getParentFile().mkdirs();
						datasystemfile.createNewFile();
					
					
						//Stream the file system out of the File
						FileInputStream fs = new FileInputStream(datasystemfile);
						
						ObjectInputStream oi = new ObjectInputStream(fs);
						Dateisystem filesys = (Dateisystem) oi.readObject();
					
						fs.close();
						oi.close();
						return filesys;
					}
					else {
						//Stream the file system out of the File
						FileInputStream fs = new FileInputStream(datasystemfile);
						
						ObjectInputStream oi = new ObjectInputStream(fs);
						
						Dateisystem filesys = (Dateisystem) oi.readObject();
						debug(debuglayer, "Vorhandenes Dateisystem wurde ausgelesen.");
						return filesys;
					
					}
					
					
			} catch (FileNotFoundException e) {
				debug(debuglayer, "Es konnte kein Dateisystem gefunden werden.");
				debug(debuglayer, "Bitte mit 'CivitasServer dateisystem init' initialisieren.");
				e.printStackTrace();
					return null;
				
			} catch (ClassNotFoundException e) { 
				debug(debuglayer, "Eine ClassNotFoundException ist aufgetreten.");
				e.printStackTrace();
					return null;
			}
		}
			
		/**
		 * Speichert das geladene Dateisystem im konstanten Pfad ab.
		 * @return 
		 */
		private static Dateisystem saveFileSystem(int debuglayer) {
			debugExec(debuglayer, "saveFileSystem...");
			Dateisystem filesys = new Dateisystem();
			String dateisystempfad = getSubfolderPathOf("dateisystem." + dateityp);
			File dateisystemfile = new File(dateisystempfad);
			
			
			debug(debuglayer, "Path: " + dateisystemfile);
			////////////////////BEGIN IO-try
			try {
				debug(debuglayer, "try..");
				dateisystemfile.getParentFile().mkdirs();
				dateisystemfile.createNewFile();
				FileOutputStream filestream = new FileOutputStream( dateisystemfile );
				ObjectOutputStream objectoutput = new ObjectOutputStream(filestream);
				objectoutput.writeObject(filesys);
				filestream.close();
				objectoutput.close();
				
				debug(debuglayer, "Vorhandenes Dateisystem wurde gespeichert.");
				////////////////////BEGIN Catch-Blöcke
					} catch (FileNotFoundException e1) {
						debug(debuglayer, "Speichern fehlgeschlagen: Es konnte kein Dateisystem gefunden werden.");
						if(debug)
							e1.printStackTrace();
						return null;
					} catch (IOException e) { 
							e.printStackTrace();
							debug(debuglayer, "IOException für " + dateisystemfile);
							
					}
				debug(debuglayer, "success!");
				////////////////////END Catch
			////////////////////END IO-try
				
			return filesys;
		}
		public static Dateisystem getFileSystem(int debuglayer) {
			debugExec(debuglayer, "getFileSystem");
			
			try {
				return readFileSystemFromPath(debuglayer + 1);
				
			} catch (IOException e) {
				return saveFileSystem(debuglayer + 1);
			}
		}
		private static void test() {
			debugExec(1, "exec test");
				System.out.println("Test!");
				exit();
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
		exitError(0);
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
		exitError(0);
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
		exitError(0);
	}
	
	/**
	 * @param The subfolder within the main data folder
	 * @return The absolute path of given subfolder
	 */
	public static String getSubfolderPathOf(String subfolder) {
		return Dateisystem.chain(dateiuri.getPath(), subfolder);
	}

	
	/**
	 *  @return The absolute path of the main data folder 
	 */
	public static String getHauptverzeichnis() {
		return dateiuri.getPath();
	}
	
	private static void exit() {
		debug(0, "Befehl ausgeführt.");
		System.exit(0);
	}
	/**
	 * 
	 * DEBUGGER of the plugin. 
	 * @param level
	 * @param nachricht
	 */
	public static void debug(int level, String nachricht) {
		if(debug != true) {
			return;
		}
		for(; level > 0; level--) {
			nachricht = "  " + nachricht;
		}
		System.out.println("[DEBUG] " + nachricht);
	}
	public static void debugExec(int debuglayer, String methodname) {
		if(!debug) return;
		debug(debuglayer - 1, "[EXECUTING]" + methodname);
		
		
	}
	private static void exitError(int status) {
		debug(0, "Bei der Ausführung trat ein Fehler auf.");
		System.exit(status);
	}
}
