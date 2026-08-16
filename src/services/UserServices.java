package services;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.GlobelExceptionHandler;
import exceptions.IncorrectCredits;
import exceptions.UserExitsAlready;
import fileHandlind.FiledataHandling;
import models.Users;

public class UserServices {
	
	ArrayList<Users> users=new ArrayList<Users>();
	public Users currentuser;
	public static String role;
	
	
	FiledataHandling filehandle;
	

	public UserServices(FiledataHandling filehandling) {
		this.filehandle=filehandling;
		this.users=filehandle.loaddata("Users.dta",users);
}

	//----------------- objects
	GlobelExceptionHandler globelexceptionhandler=new GlobelExceptionHandler();


Scanner sc=new Scanner(System.in);


//---------------------------generate id
	public int generateid() {
		if(users.size()==0) {
			return 1;
		}
		return users.get(users.size()-1).getUserid()+1;
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
	
	public void Register(){
		
		System.out.println("=========================\r\n"
				+ "Register\r\n"
				+ "=========================");
		System.out.println("username : ");
	    String username=sc.nextLine();
	    System.out.println("password : ");
	    String password=sc.nextLine();
	    System.out.println("phonenumber");
	    Long phonenumber=sc.nextLong();
	    sc.nextLine();
	   
	    System.out.println("Address : ");
	    String Address= sc.nextLine();
	  
	    if(registervaildation(username,phonenumber))  {
		    Users u=new Users(generateid(),username,password,phonenumber,Address,"user");
		    users.add(u);
		    filehandle.savedata("Users.dta",users);
		    
	    }else {
	    	
	    	UserExitsAlready e=new UserExitsAlready("User Exits Already");
	    	globelexceptionhandler.handler(e);
	         return ;
	    }    
	}
	
	
	
	
	//-------------------------login
	public Users login(String username,String password) {
		Users CurrentUser=new Users(0,"admin","admin",000,"hyd","admin");
		if(username.equals("admin") && password.equals("admin")) return CurrentUser;
		else {
			for(Users ele:users) {
				if(ele.getName().equals(username) && ele.getPassword().equals(password)) return ele;
			}
		}
		IncorrectCredits e=new IncorrectCredits("Incorrect Credits");
		globelexceptionhandler.handler(e);
			
		return null;
	}
	
	
	
	
	//------------------------------ViewUsers
	public void ViewUsers() {
		
		
		System.out.println("==============================================");
		System.out.println("                 ALL USERS");
		System.out.println("==============================================");
		System.out.printf("%-6s %-14s %-14s %s%n", "ID", "Username", "Phone", "Address");
		System.out.println("------------------------------------------------");

		for (Users ele : users) {
		        System.out.printf("%-6s %-14s %-14s %s%n", 
		            String.valueOf(ele.getUserid()), 
		            String.valueOf(ele.getName()), 
		            String.valueOf(ele.getPhonenumber()), 
		            String.valueOf(ele.getAddress())
		        );
		}
		System.out.println("\n==============================================");

	}
	

	
	
	
	//---------------------register vaildation
	public boolean registervaildation(String username,Long phonenumber){
		for(Users u:users) {
			if(u.getName().equals(username) && u.getPhonenumber()==phonenumber) return false ;
		}
		
	return true;
	}
}



//----------------------Userlogin
	
//	public Users userlogin(String username,String password) {
//		Users Currentuser=null;
//		for(Users ele:users) {
//			if(ele.getName().equals(username) && ele.getPassword().equals(password)) currentuser=ele;
//		}
//		if(currentuser.equals(null)) {
//			IncorrectCredits e=new IncorrectCredits("Incorrect Credits");
//			globelexceptionhandler.handler(e);
//			
//		}
//		return Currentuser;
//			
//	}
		
		
		
		
	
	
//------------------------adminlogin	
//	public boolean adminlogin(String username,String password) {
//		if(username.equals("admin") && password.equals("admin")) {
//			return true;
//		}
//		return false;
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//--------------------login validation
	//public Users loginvalidation(String username,String password) {
//		
//		
//		
//		for(Users ele:users) {
//			if(ele.getName().equals(username) && ele.getPassword().equals(password)) {
//				currentuser=ele;
//				role="user";
//				break;
//			}
//		}
//		
//	}
