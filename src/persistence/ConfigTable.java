package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Zum Zugriff auf die Konfigurationen des Archivprogramms
 * 
 * @author Kerstin
 *
 */
public class ConfigTable {
	private String sourceDir;
	private String destinationDir;
	private boolean manualStore;

	/**
	 * holt aus der Datenbank, die Konfigurationswerte sind noch keine
	 * vorhanden, werden Dumywerte angelegt
	 */
	public ConfigTable() {
		// Verbindungsaufbau mit Java7
		if (!isConfigEmpty()) {
			try (Connection con = DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
					Statement stmt = con.createStatement();) {
				String sql = "SELECT * FROM config;";
				ResultSet rs = stmt.executeQuery(sql);
				// auf den ersten Datensatz setzen - wenn keiner da wird
				// die while-Schleife übersprungen
				while (rs.next()) {
					sourceDir = rs.getString("sourceRoot");
					destinationDir = rs.getString("destinationRoot");
					String manualStoreHelp = rs.getString("manualStore");
					if (manualStoreHelp.equals("true")) {
						manualStore = true;
					} else {
						manualStore = false;
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			sourceDir = "bitte Quell-Verzeichnis auswählen";
			destinationDir = "bitte Ziel-Verzeichnis auswählen";
			manualStore = true;
		}

	}

	// Getter und Setter
	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}

	public boolean isManualStore() {
		return manualStore;
	}

	public void setManualStore(boolean manualStore) {
		this.manualStore = manualStore;
	}

	/**
	 * ermittelt ob schon ein Eintrag in config vorhanden ist - dann darf kein
	 * zweiter erfolgen
	 * 
	 * @return true = kein Eintrag vorhanden , false = Eintrag vorhanden
	 */
	private boolean isConfigEmpty() {
		boolean result = false;
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
				Statement stmt = con.createStatement();) {
			// zählt die Zeilen in der Tabelle
			String sql = "SELECT COUNT(*) FROM config;";
			ResultSet rs = stmt.executeQuery(sql);
			// Auf den Ersten Datensatz positionieren
			rs.next();
			// gibt das Ergebnis der Abfrage an den return weiter
			result = (rs.getInt(1) == 0) ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Eintragen des Eintrags in die Tabelle config (nur wenn noch kein Eintrag
	 * vorhanden ist) _id ist immer 1
	 * 
	 * @param source
	 *            das Quellverzeichnis aus dem die Dateien üblicherweise kommen
	 * @param destination
	 *            das Zielverzeichnis von dem aus der Baum aufgebaut wird
	 * @param manual
	 *            true= manuelle Ablage, false = automatische Ablage
	 * @return true= Zeile wurde hinzugefügt, oder es gab schon eine Zeile
	 *         false= irgendwas ging schief
	 */
	public boolean insertConfig(String source, String destination, String manual) {
		boolean result = false;
		if (isConfigEmpty()) {
			// nur wenn es noch keinen Eintrag gibt, darf hinzugefügt werden
			try (Connection con = DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
					Statement stmt = con.createStatement();) {
				String sql = "INSERT INTO config  (_id, " + "sourceRoot, " + "destinationRoot, " + "manualStore) "
						+ "VALUES (" + 1 + ", '" + source + "', " + "'" + destination + "', " + "'" + manual + "');";
				// executeUpdate wirft keinen Fehler, wenn die Anweisung nichts
				// einfügt
				// darum excplizieter Check
				int erfolg = stmt.executeUpdate(sql);
				if (erfolg == 1) {
					sourceDir = source;
					destinationDir = destination;
					//// noch korrigieren!!!!
					manualStore = false;
					result = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// wird zwar nix eingefügt, da aber nur eine Zeile reindarf ist
			// trotzdem alles ok
			result = updateConfigSource(source) & updateConfigDestination(destination);
		}

		return result;
	}

	public boolean updateConfigSource(String source) {
		boolean result = false;
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
				Statement stmt = con.createStatement();) {
			String sql = "Update config  SET sourceRoot = '" + source + "' WHERE _id =1;";
			// executeUpdate wirft keinen Fehler, wenn die Anweisung nichts
			// ändert
			// darum excplizieter Check
			int erfolg = stmt.executeUpdate(sql);
			if (erfolg == 1) {
				result = true;
				sourceDir = source;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateConfigDestination(String destination) {
		boolean result = false;
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
				Statement stmt = con.createStatement();) {
			String sql = "Update config  SET destinationRoot = '" + destination + "' WHERE _id =1;";
			// executeUpdate wirft keinen Fehler, wenn die Anweisung nichts
			// ändert
			// darum excplizieter Check
			int erfolg = stmt.executeUpdate(sql);
			if (erfolg == 1) {
				result = true;
				destinationDir = destination;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateConfigDestination() {
		boolean result = false;
		return result;
	}

	public boolean updateConfigManual() {
		boolean result = false;
		return result;
	}
}
