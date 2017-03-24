package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import application.KeyWord;
import javafx.collections.ObservableList;

public class TestDb {

	public TestDb() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int newID= KeywordTable.getHighestID()+1;
		//Reihenfolge: id, keyword,pfad,parent,level
		KeyWord keyword= new KeyWord(newID, "Versicherung", "Versicherung", 3, 2);
		KeywordTable.insertKeyword(keyword);
		ObservableList<KeyWord> l=KeywordTable.selectLevel(1, false);
		for(KeyWord k:l){
			System.out.println(k);
		}
		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
				Statement stmt=con.createStatement();){
				String sql;
				//sql="UPDATE childParent SET child=2 WHERE id=1";
				sql="UPDATE keywords SET keyword='Heiko', pathName='Heiko' WHERE id=3";
				int count=stmt.executeUpdate(sql);
				System.out.println(count);
				
		} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		ObservableList<KeyWord>ol=KeywordTable.getChildren(2, false);
		for(KeyWord k:ol){
			System.out.println(k);
		}
	}

}
