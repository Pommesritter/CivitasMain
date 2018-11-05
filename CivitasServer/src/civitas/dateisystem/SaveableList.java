package civitas.dateisystem;

public enum SaveableList {
	
	WORLD( "spieldateien/welten" );
	
	private String path = "";
	
	/**
	 * @param filepath : Ordnerpfad, in dem die Datei abgelegt werden soll.
	 */
	private SaveableList(String filepath) {
		this.path=filepath;
	}
	
	public String getFolderPath() {
		return path;
	}
}
