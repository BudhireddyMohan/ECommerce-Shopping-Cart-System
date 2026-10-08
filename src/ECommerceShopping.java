import java.util.Scanner;

import Context.ApplicationContext;
import exceptions.GlobelExceptionHandler;
import models.Users;

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
    	
    	
    	
    	
    	
    	// 1. Wrap the original system input stream so close() commands are completely ignored
    	java.io.InputStream uncloseableInputStream = new java.io.FilterInputStream(System.in) {
    	    @Override
    	    public void close() throws java.io.IOException {
    	        // Do absolutely nothing! This blocks the stream from shutting down.
    	    }
    	};

    	// 2. Override Java's default System.in with our uncloseable wrapper
    	System.setIn(uncloseableInputStream);


        GlobelExceptionHandler g = new GlobelExceptionHandler();

        // Objects
        ApplicationContext objectcontainer =  ApplicationContext.getinstance();

        // Application started
        Scanner sc = new Scanner(System.in);

        boolean t = true;
        while (t) {

        	
            try {

                applicationstart();
                int choice =  Integer.parseInt(sc.nextLine());
                switch (choice) {

                    // REGISTER
                    case 1: {

                        objectcontainer
                                .getUserservices()
                                .Register();

                        break;
                    }

                    // LOGIN
                    case 2: {

                        System.out.println("=========================");
                        System.out.println("Login");
                        System.out.println("=========================");

                        System.out.println("enter the username");
                        String username = sc.nextLine();

                        System.out.println("enter the password");
                        String password = sc.nextLine();

                        Users currentuser =
                                objectcontainer
                                        .getUserservices()
                                        .login(username, password);

                        System.out.println(
                                "current user = " + currentuser
                        );

                        if (currentuser != null) {

                            switch (currentuser.getRole()) {

                                case "USER": {

                                    objectcontainer
                                            .getUsermenu()
                                            .welcomeusermenu(currentuser);

                                    break;
                                }

                                case "admin": {

                                    objectcontainer
                                            .getAdminmenu()
                                            .welocomeadminmenu();

                                    break;
                                }
                            }
                        }

                        break;
                    }

                    // EXIT
                    case 3: {

                        System.out.println("Thank you........");
                        t = false;
                        break;
                    }

                   
                    default: {

                        System.out.println(
                                "enter correct option"
                        );
                    }
                }

            } catch (Exception e) {

                // Global exception handler
                g.handler(e);
            }
        }
    }
}