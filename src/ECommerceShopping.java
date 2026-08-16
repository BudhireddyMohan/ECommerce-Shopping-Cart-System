import java.util.Scanner;

import Context.ApplicationContext;
//import menus.Adminmenu;

//import menus.Usermenu;
import models.Users;

//import services.UserServices;



public class ECommerceShopping {

	
	public static void applicationstart() {
		System.out.println();
		System.out.println();
		System.out.println("=========================================");
		System.out.println(" Welcome to Shopping Cart System");
		System.out.println("=========================================");
		System.out.println("1. Register");
		System.out.println("2. login");
		System.out.println("3. exit");
		System.out.println("Enter Choice:");
		
}
	
	 
	
	
	public static void main(String[] args) {
		
//------------------------ objects
		ApplicationContext objectcontainer=new ApplicationContext();
	
		
//------------------Applcation started
		Scanner sc=new Scanner(System.in);
		
		boolean t=true;
		while(t) {
		applicationstart();
		int choice=sc.nextInt();
		sc.nextLine();
		
		
		switch(choice) {
		// register
		case 1:{
		objectcontainer.getUserservices().Register();
			break;
		}
		//login
		case 2:{
			System.out.println("=========================\r\n"
					+ "Login\r\n"
					+ "=========================");
			System.out.println("enter the username");
			String username = sc.nextLine();

			System.out.println("enter the password");
			String password = sc.nextLine();

			Users currentuser = objectcontainer.getUserservices().login(username, password);
			System.out.println("current user = " + currentuser);

			if (currentuser != null) {
			    switch (currentuser.getRole()) {
			        case "user":{
			            objectcontainer.getUsermenu().welcomeusermenu(currentuser);
			            break;
			        }
			        
			        case "admin":{
			        	
			        	objectcontainer.getAdminmenu().welocomeadminmenu();
			            break;
			        }
			    }
			}
			break;
		}case 3:{
			System.out.println("Thank you........");
		  	t=false;
		  	break;
		}
		default:{
			System.out.println("enter correct option");
		}
		
		}
         
	}sc.close();
		}
	}


