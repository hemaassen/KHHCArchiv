package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Initialisierung der Datenbank muss mit in die Auslieferung
 * 
 * @author Kerstin
 *
 */
public class InitDB {
	public static void main(String args[]) {
		
		try (Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
			Statement stmt=con.createStatement();	){
			String sql;
			//Anlegen der Tabelle config
			sql = "DROP Table If EXISTS config"; 
			stmt.executeUpdate(sql);            
			
			sql = "CREATE TABLE config " + "( '_id' INTEGER PRIMARY KEY  AUTOINCREMENT,"
					+ " sourceRoot           VARCHAR(255)    NOT NULL, " + " destinationRoot            VARCHAR(255)     NOT NULL, "
					+ " manualStore        CHAR(5) DEFAULT true)";
			stmt.executeUpdate(sql);
			//Anlegen der Tabelle keywords
			sql = "DROP Table If EXISTS keywords"; 
			stmt.executeUpdate(sql);               
			
			sql = "CREATE TABLE keywords " + "( 'id' INTEGER PRIMARY KEY  AUTOINCREMENT,"
					+ " keyWord           VARCHAR(50)    NOT NULL, " 
					+ " pathName            VARCHAR(50)     NOT NULL, "
					+ " level     INTEGER  NOT NULL)";
					
			stmt.executeUpdate(sql);
			//Anlegen der Tabelle childParant
			sql = "DROP Table If EXISTS childParent";  
			stmt.executeUpdate(sql);                   
			
			sql = "CREATE TABLE childParent " + "( 'id' INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " child    INTEGER  NOT NULL, " 
					+ " parent     INTEGER  NOT NULL, " 
					+ " FOREIGN KEY(child) REFERENCES keywords(id) ON DELETE CASCADE,"
					+ " FOREIGN KEY(parent) REFERENCES keywords(id) ON DELETE CASCADE)";
					
			stmt.executeUpdate(sql);
			//Startwert in Tabelle keywords eintragen
			sql="INSERT INTO keywords (keyWord, pathName, level) "
					+ "VALUES('Neuer Eintrag..', 'xxx', 0);";
					
			stmt.executeUpdate(sql);
			sql="INSERT INTO keywords (keyWord, pathName, level) "
					+ "VALUES('', '', 0);";
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println(e.getMessage());
			
		}
		
	}
}
