package Lab1Web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager {
	 static String dbUrl = "jdbc:postgresql://localhost:5432/studs";
	 static String dbUser = "s467846";
	 static String dbPassword = "S7KoN69grNgAcrHc";
	 
	 public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	    }
	 
	 
	 public static void saveResult(String json) {
	        String sql = "INSERT INTO results (data) VALUES (?::json)";
	        try (Connection conn = getConnection(); 
	        		PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, json);
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
	 public static List<String> getAllJson() {
	        List<String> jsonList = new ArrayList<>();
	        String sql = "SELECT data FROM results"; 

	        try (Connection conn = getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                String jsonData = rs.getString("data");
	                jsonList.add(jsonData);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return jsonList;
	    }
	 
	 
	 public static List<String> loadNextPage(int pageNumber){
		 if(pageNumber % 5 == 0 ) pageNumber+=1;
		 List<String> jsonPage = new ArrayList<>();
		 String sqlQuery = "SELECT data FROM results ORDER BY id DESC LIMIT ? OFFSET ?";
		 
		 try(Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sqlQuery);){
			 stmt.setInt(1, 100);
			 stmt.setInt(2, (pageNumber-1)*20);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while(rs.next()){
				 String jsonData = rs.getString("data");
				 jsonPage.add(jsonData);
			 }
		 }
		 catch (SQLException e) {
	            e.printStackTrace();
	      }
		 	
		 return jsonPage;
	 }
	 
}
