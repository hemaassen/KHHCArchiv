package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.KeyWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KeywordTable {

	public KeywordTable() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Beim Eintragen eines neuen Schlüsselwortes müssen 2 Dinge erfolgen:
	 * einmal der Eintrag in der Keywords-Tabelle und dann der Eintrag in der childParent-Tabelle
	 * Scheitert einer davon ist der andere ebenfalls hinfällig
	 * @param k - KeyWord
	 * @return true Eintrag wurde erfolgreich in der Datenbank abgelegt
	 */
	public static boolean insertKeyword(KeyWord k){
		boolean result=false;
		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
			Statement stmt=con.createStatement();){
			String sql;
			//transaktion starten
			con.setAutoCommit(false);
			con.setSavepoint();
			sql="INSERT INTO keywords (keyWord, pathName, level) "
					+ "VALUES('" + k.getKeyword() +"', '"
					+ k.getPath() + "', "
					+ k.getLevel()+");";
			int count=stmt.executeUpdate(sql);
			if(count>0){
				//in keywords schon mal drin
				sql="INSERT INTO childParent (child, parent)  "
						+ "VALUES(" +(getHighestID()+1) + ", "
						+ k.getParent() +");";
				count=stmt.executeUpdate(sql);
				if (count>0){
					//beide Einträge drin
					result=true;
					con.commit();
				}else{
					//in die ChildParent konnte nicht geschrieben werden
					con.rollback();
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * liefert die höchste vergebene ID in der keywords-Tabelle
	 * @return
	 */
	public static Integer getHighestID() {
		Integer result=0;
		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
			Statement stmt=con.createStatement();){
			String sql;
			sql="SELECT MAX(id) FROM keywords;";
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next()) {
				result=(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * liefert eine Liste aller Schlüsselwörter eines bestimmten levels
	 * @param level
	 * @return
	 */
	public static ObservableList<KeyWord> selectLevel(int level){
		List<KeyWord> list=new ArrayList<>(); 
		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
				Statement stmt=con.createStatement();){
				String sql;
				//erstmal alle Schlüsselwörter des levels und den standardwert holen
				sql="SELECT * FROM keywords WHERE level = " + level+ " OR level = 0;";
				ResultSet rs=stmt.executeQuery(sql);
				while (rs.next()) {
					//parent ist noch leer (beim Standardwert gibt es kein Parent)
					KeyWord k=new KeyWord(rs.getInt("id"),
							rs.getString("keyword"),
							rs.getString("pathName"),
							0,
							rs.getInt("level"));
					Statement stmt1=con.createStatement();
					//jetzt hole ich das Elternteil
					ResultSet rs1=stmt1.executeQuery(
							"SELECT parent FROM childParent WHERE child = " +rs.getInt("id")+ ";");
					while(rs1.next()){
						//der Standardwert hat keinen Elternteil darum liefert das resultset.next false 
						k.setParent(rs1.getInt(1));
					}
					list.add(k);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ObservableList<KeyWord> result= FXCollections.observableArrayList(list);
		return result;
	}
	public static ObservableList<KeyWord> getChildren(Integer id){
		List<KeyWord> list=new ArrayList<>(); 
		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
				Statement stmt=con.createStatement();){
				String sql;
				//erstmal alle Schlüsselwörter des levels und den standardwert holen
				sql="SELECT * FROM keywords WHERE id IN(SELECT child FROM childParent WHERE parent=" + id+ ") OR level = 0;";
				ResultSet rs=stmt.executeQuery(sql);
				while (rs.next()) {
					//parent ist noch leer (beim Standardwert gibt es kein Parent)
					KeyWord k=new KeyWord(rs.getInt("id"),
							rs.getString("keyword"),
							rs.getString("pathName"),
							0,
							rs.getInt("level"));
					Statement stmt1=con.createStatement();
					//jetzt hole ich das Elternteil
					ResultSet rs1=stmt1.executeQuery(
							"SELECT parent FROM childParent WHERE child = " +rs.getInt("id")+ ";");
					while(rs1.next()){
						//der Standardwert hat keinen Elternteil darum liefert das resultset.next false 
						k.setParent(rs1.getInt(1));
					}
					list.add(k);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ObservableList<KeyWord> result= FXCollections.observableArrayList(list);
		return result;
		
	}
}
