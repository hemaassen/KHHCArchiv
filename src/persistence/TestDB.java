package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import application.KeyWord;

public class TestDB {

	public TestDB() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
//		int newID= KeywordTable.getHighestID()+1;
//		KeyWord keyword= new KeyWord(newID, "Kerstin", "Kerstin", 1, 1);
//		KeywordTable.insertKeyword(keyword);
//		List<KeyWord> l=KeywordTable.selectLevel(1);
//		for(KeyWord k:l){
//			System.out.println(k);
//		}
//		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
//				Statement stmt=con.createStatement();){
//				String sql;
//				//sql="UPDATE childParent SET child=2 WHERE id=1";
//				sql="UPDATE keywords SET keyword='Heiko', pathName='Heiko' WHERE id=3";
//				int count=stmt.executeUpdate(sql);
//				System.out.println(count);
//				
//		} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
	}

}
