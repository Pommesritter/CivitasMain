package civitas.dateisystem;

public enum CivitasSaveableObject {
	
	WORLD( "spieldateien/welten" );
	
	private String path = "";
	
	/**
	 * @param filepath : Ordnerpfad, in dem die Datei abgelegt werden soll.
	 */
	private CivitasSaveableObject(String filepath) {
		this.path=filepath;
	}
	
	public String getFolderPath() {
		return path;
	}
}
