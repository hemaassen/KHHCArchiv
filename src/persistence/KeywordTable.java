package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import application.KeyWord;

public class KeywordTable {

	public KeywordTable() {
		// TODO Auto-generated constructor stub
	}
	public static boolean insertKeyword(KeyWord k){
		boolean result=false;
		try(Connection con=DriverManager.getConnection("jdbc:sqlite:database/archiv.db");
			Statement stmt=con.createStatement();){
			String sql;
			sql="INSERT INTO keywords (keyWord, pathName, level) "
					+ "VALUES('" + k.getKeyword() +"', '"
					+ k.getPath() + "', "
					+ k.getLevel()+");";
			int count=stmt.executeUpdate(sql);
			if(count>0){
				result=true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}

}
