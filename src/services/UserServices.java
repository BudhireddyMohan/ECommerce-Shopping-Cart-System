package services;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import Repository.DAOs.UserDAO;
import exceptions.GlobelExceptionHandler;
import exceptions.IncorrectCredits;
import exceptions.UserExitsAlready;
import models.Users;

public class UserServices {
	

	public Users currentuser;
	
	
	//objects
	
	GlobelExceptionHandler globelexceptionhandler;
	UserDAO userdao;
	
	
	
	public UserServices(GlobelExceptionHandler globalhandler,UserDAO userdao) {
		
		this.globelexceptionhandler=globalhandler;
		this.userdao=userdao;
}

	
Scanner sc=new Scanner(System.in);


//---------------------------generate id
	public static int generateid() {
		
		 SecureRandom random = new SecureRandom();
		    return random.nextInt(Integer.MAX_VALUE);
	}
	
//-------------------------MyProfile
	
	public void MyProfile(Users currentuser) {
		System.out.println("==================");
		System.out.println("     Profile");
		System.out.println("=================");
		System.out.println("Name : "+currentuser.getName());
		System.out.println("Phone Number : "+currentuser.getPhonenumber());
		System.out.println("address : "+currentuser.getAddress() );
	}
		
	
//------------------------register 	
	
	public void Register() throws UserExitsAlready,SQLException{
		
		System.out.println("=========================\r\n"
				+ "Register\r\n"
				+ "=========================");
		System.out.println("email : : ");
	    String email=sc.nextLine();
	    System.out.println("password : ");
	    String password=sc.nextLine();
	    System.out.println("phonenumber");
	    Long phonenumber=sc.nextLong();
	    sc.nextLine();
	   
	    System.out.println("Address : ");
	    String Address= sc.nextLine();
	  
	    if(userdao.RegistrationValidation(email, phonenumber))  {
		    Users u=new Users(generateid(),email,password,phonenumber,Address,"USER");
		    userdao.saveregisterdata(u);
		    
		    
	    }else {
	    	 new UserExitsAlready("User Exits Already");
	    }    
	}
	
	
	
	
	//-------------------------login
	
	public Users login(String username,String password) throws SQLException,IncorrectCredits {
		Users CurrentUser=new Users(0,"admin","admin",000,"hyd","admin");
		if(username.equals("admin") && password.equals("admin")) return CurrentUser;
			Optional<Users> userdetails=userdao.logincheck(username,password);
		return userdetails.orElseThrow(()->new IncorrectCredits("Incorrect Credits"));	
		
		
	}
	
	
	
	
	//------------------------------ViewUsers
	public void ViewUsers() throws SQLException {
		
		
		System.out.println("==============================================");
		System.out.println("                 ALL USERS");
		System.out.println("==============================================");
		System.out.printf("%-6s %-14s %-14s %s%n", "ID", "Username", "Phone", "Address");
		System.out.println("------------------------------------------------");
        ArrayList<Users> al= userdao.GetAllUsers() ;
      
		for (Users ele : al) {
		        System.out.printf("%-6s %-14s %-14s %s%n", 
		            String.valueOf(ele.getUserid()), 
		            String.valueOf(ele.getName()), 
		            String.valueOf(ele.getPhonenumber()), 
		            String.valueOf(ele.getAddress())
		        );
		}
		System.out.println("\n==============================================");

	}
	

	
	
	
}



