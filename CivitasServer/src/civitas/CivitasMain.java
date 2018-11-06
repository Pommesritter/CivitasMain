package civitas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;

import civitas.dateisystem.CivitasSaveable;
import civitas.dateisystem.SaveableList;
import civitas.dateisystem.FileSystem;
import civitas.world.World;

public class CivitasMain {
	
	//Wird beim starten auf das Verzeichnis gesetzt, in dem die .jar ausgeführt wird.
	private static URI dateiuri;
	
	public static final String dateisystemname = "dateisystem";
	public static final String dateityp = ".cvfile";
	/**
	 * Liste der erlaubten Hauptargumente
	 * Jedes erlaubte Argument muss hier eingetragen sein.
	 * Wenn nicht eingetragene Argumente verwendet werden, 
	 * bricht das Programm mit Code 0 ab.
	 */
	private final static String[] erlaubteArgumente = { "dateisystem", "test" };
	//und so weiter...
	private final static String[] erlaubteArgumenteDateisystem = { "init", "check" };

	private final static String[] erlaubteArgumenteTest = { "welt [create / readinfo]" };
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
			erlaubteArgsAusgeben("", erlaubteArgumente);
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
						} } else erlaubteArgsAusgeben("dateisystem", erlaubteArgumenteDateisystem);
					
				return;
				//////////////////////////////////////////////////////////////
				
	//end///////////////ENDE BEFEHLSPARAMETER DATEISYSTEM/////////////////////
				
				
	//begin////////////BEFEHLSPARAMETER "test"////////////////////////////////
				case "test": {
					test(args, debuglayer);
					return;      
				}
	//end///////////////ENDE BEFEHLSPARAMETER TEST////////////////////////////
				
			}
			
//end/////////////////////////////////////////////////////////////////////////
//end////////////ENDE HAUPTSWITCH CIVITASSERVER///////////////////////////////
//end/////////////////////////////////////////////////////////////////////////
		System.out.println("Bitte einen gültigen Befehl eingeben: ");
		erlaubteArgsAusgeben("", erlaubteArgumente);
		exit();
		
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

			//CivitasServer test
			if(args.length == 1 ) {
				test();
				return;
			}
				
			switch(args[1]) {
				//CivitasServer test welt ...
				case "welt" : 
					{
					String testname = "test_world";
					int testindex = 9999;
						
					if(args.length == 2) {
						//CivitasServer test welt
						erlaubteArgsAusgeben("test", erlaubteArgumenteTest);
						return;
						}
					
					//CivitasServer test welt [...]
						
					switch(args[2]) {
						case "create" : {
							debug(debuglayer, "Erstelle und speichere Welt mit dem Testindex " + testindex + "...");
							World neueWelt = new World(testname, testindex);
							getFileSystem(debuglayer + 1).speichern(neueWelt);
						
							return;
						}
						case "readinfo": {
							debug(debuglayer, "reading existing test world...");
							World w = (World) read(debuglayer + 1, SaveableList.WORLD, testname);
							if(w == null) {
								debug(debuglayer, "test world was not found");
								return;
							}
							out("Test world name: " + w.getName());
							out("Test world index: " + w.getListenposition());
							out("Test world relative path: " + w.getRelativeFilePath());
							return;
						}
					}
					debug (debuglayer, "???");
					//CivitasServer test welt [??????]
					erlaubteArgsAusgeben("test", erlaubteArgumenteTest);
				}
			}
			exit();
			
		}
		
		/**
		 * Liest das aktuelle Dateisystem 
		 * Der Pfad ist in dieser Klasse angegeben (Konstante "dateiuri")
		 */
		private static FileSystem readFileSystemFromPath(int debuglayer) throws IOException {
			debugExec(debuglayer, "readFileSystemFromPath...");
			String dateisystempfad = getSubfolderPathOf("dateisystem" + dateityp);
			
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
						FileSystem filesys = (FileSystem) oi.readObject();
					
						fs.close();
						oi.close();
						return filesys;
					}
					else {
						//Stream the file system out of the File
						FileInputStream fs = new FileInputStream(datasystemfile);
						
						ObjectInputStream oi = new ObjectInputStream(fs);
						
						FileSystem filesys = (FileSystem) oi.readObject();
						oi.close();
						debug(debuglayer, "Vorhandenes Dateisystem wurde ausgelesen.");
						return filesys;
					
					}
					
					
			} catch (FileNotFoundException e) {
				debug(debuglayer, "Es konnte kein Dateisystem gefunden werden.");
				debug(debuglayer, "Bitte mit 'CivitasServer dateisystem init' initialisieren.");
				e.printStackTrace();
					return null;
				
			} catch (ClassNotFoundException e) { 
				debug(debuglayer, "FAILED: ClassNotFoundException");
				e.printStackTrace();
				exitError("could not read file system. Aborting task...");
				return null;
			}
		}
			
		/**
		 * Speichert das geladene Dateisystem im konstanten Pfad ab.
		 * @return 
		 */
		private static FileSystem saveFileSystem(int debuglayer) {
			debugExec(debuglayer, "saveFileSystem...");
			FileSystem filesys = new FileSystem();
			String dateisystempfad = getSubfolderPathOf("dateisystem" + dateityp);
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
				
				debug(debuglayer, "existing file system has been saved.");
				////////////////////BEGIN Catch-Blöcke
					} catch (FileNotFoundException e1) {
						debug(debuglayer, "FAILED: file " + dateisystemname + dateityp +" was not found on disk.");
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
		public static FileSystem getFileSystem(int debuglayer) {
			debugExec(debuglayer, "getFileSystem");
			
			try {
				return readFileSystemFromPath(debuglayer + 1);
				
			} catch (IOException e) {
				return saveFileSystem(debuglayer + 1);
			}
		}
		private static void test() {
			debugExec(1, "exec test");
				out("Test!");
				exit();
		}
	
	/**
	 * @param The subfolder within the main data folder
	 * @return The absolute path of given subfolder
	 */
	public static String getSubfolderPathOf(String subfolder) {
		return FileSystem.chain(dateiuri.getPath(), subfolder);
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
	public static void out(String s) {
		System.out.println(s);
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
		out("[DEBUG] " + nachricht);
	}
	public static void debugExec(int debuglayer, String methodname) {
		if(!debug) return;
		debug(debuglayer - 1, "[executing]" + methodname);
		
		
	}
	private static void exitError(String string) {
		debug(0, string);
		System.exit(1);
	}
	public static void erlaubteArgsAusgeben(String befehl, String[] allowedArgs) {
		String temp = "Allowed arguments for '" + befehl + "'";
		out(temp);
		
		for(String s : allowedArgs) {
			out("* " + s);
		}
	}
	
	public static CivitasSaveable read(int debuglayer, SaveableList obj, String name){
		
		switch(obj) {
			//READ WORLD
			case WORLD :
				try {
				World w = (World) getFileSystem(debuglayer).lesen(obj, name);
				return w;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
						} catch (FileNotFoundException e) {
							out("File for testing world could not be read.");
								} catch (IOException e) {
									out("I/O : File for testing world could not be read.");
				}
			//READ OTHER OBJECTS 
			
			
		}
		
	//IF NONE WAS FOUND; RETURN NULL
	return null;
	}
}
