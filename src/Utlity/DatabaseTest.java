package Utlity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
	
	  String url = "jdbc:mysql://localhost:3306/ecommers";
      String user = "root";
      String password = "Mohan@2004";
      
      
     public Connection getconnection()  throws SQLException{ 
    	
    		  return   DriverManager.getConnection(url, user, password);   
 	
     }
}


