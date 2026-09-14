package Repository.DAOimplement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import Repository.DAOs.UserDAO;
import Utlity.DatabaseTest;
import models.Users;

public class UserDAOimplements implements UserDAO{
	
	
	
	
	//objects
	DatabaseTest database;
	
	
	public UserDAOimplements(DatabaseTest database){
		this.database=database;
	}
	
	
	@Override
	public void saveregisterdata(Users user) throws SQLException  {
		
	
		    Connection con=database.getconnection();
			CallableStatement c=con.prepareCall("call insertdatatousers(?,?,?,?,?,?)");
			c.setInt(1,user.getUserid());
			c.setString(2, user.getName());
			c.setString(3, user.getPassword());
			c.setString(4, user.getAddress());
			c.setLong(5, user.getPhonenumber());
		    c.setString(6, user.getRole());
		     System.out.println("Added to Database : "+ c.executeUpdate()+"Registred Sucessfully");	
		
     }


	@Override
	public Optional<Users> logincheck(String username, String password) throws SQLException{
		 Connection con=database.getconnection();
		 String query="select * from users where name=? and password=?";
		 PreparedStatement ps= con.prepareStatement(query);
		 ps.setString(1, username);
		 ps.setString(2, password);
		 ResultSet rs=ps.executeQuery();
		 
		 if(rs.next()) {
			 Users user=new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(5),rs.getString(4),rs.getString(6));
			 return Optional.of(user);
		 }else {
			 return Optional.empty(); 
		 }
		 
		 
		
	}


	@Override
	public ArrayList<Users> GetAllUsers() throws SQLException {
		 Connection con=database.getconnection();
		Statement s= con.createStatement();
		String query="select * from Users";
		ResultSet rs=s.executeQuery(query);
		ArrayList<Users> al=new ArrayList<>();
		while(rs.next()) {
			 Users user=new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(5),rs.getString(4),rs.getString(6));
			al.add(user);
		 }
		return al;
	}


	@Override
	public boolean RegistrationValidation(String email, Long phonenumber) throws SQLException {
		Connection con=database.getconnection();
		String query="select * from users where name=? and phonenumber=?;";
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, email);
		ps.setLong(2, phonenumber);
		return ps.execute();
	}
	

}
