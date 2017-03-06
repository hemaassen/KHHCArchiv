package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import application.KeyWord;

public class InitDB {
	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
			System.out.println("Opened database successfully");
			String sql;
			stmt = c.createStatement();
			sql = "DROP Table If EXISTS config"; //holger
			stmt.executeUpdate(sql);             //holger
			
			sql = "CREATE TABLE config " + "( '_id' INTEGER PRIMARY_KEY  AUTO_INCREMENT   NOT NULL,"
					+ " sourceRoot           VARCHAR(255)    NOT NULL, " + " destinationRoot            VARCHAR(255)     NOT NULL, "
					+ " manualStore        CHAR(5) DEFAULT true)";
			stmt.executeUpdate(sql);
			stmt.close();
			
			sql = "DROP Table If EXISTS keywords"; //holger
			stmt.executeUpdate(sql);               //holger
			
			sql = "CREATE TABLE keywords " + "( 'id' INTEGER PRIMARY KEY  AUTOINCREMENT,"
					+ " keyWord           VARCHAR(50)    NOT NULL, " + " pathName            VARCHAR(50)     NOT NULL, "
					+ " level     INTEGER  NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			
			sql = "DROP Table If EXISTS childParent";  //holger
			stmt.executeUpdate(sql);                   //holger
			
			sql = "CREATE TABLE childParent " + "( 'id' INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " child    INTEGER  NOT NULL, " + " parent     INTEGER  NOT NULL, " + " FOREIGN KEY(child) REFERENCES keywords(id),"
					+ " FOREIGN KEY(parent) REFERENCES keywords(id))";
			stmt.executeUpdate(sql);
			stmt.close();
			
			sql="INSERT INTO keywords (keyWord, pathName, level) "
					+ "VALUES('Neuer Eintrag..', 'xxx', 0);";
					
			int count=stmt.executeUpdate(sql);
			
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}
}
