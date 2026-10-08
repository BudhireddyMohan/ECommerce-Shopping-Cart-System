package menus;

import java.util.Scanner;

import exceptions.GlobelExceptionHandler;
//import Context.ApplicationContext;
import models.Users;
import services.CartServices;
import services.OrderServices;
import services.ProductsServices;
import services.UserServices;



public class Usermenu {
	

	
	
	//------------object
	
	Users currentuser;
	ProductsServices productservices;
	CartServices cartservices;
	ProductsServices productservices2;
	OrderServices orderservices;
	UserServices userservices;
	GlobelExceptionHandler g=new GlobelExceptionHandler();
		    
	public Usermenu( ProductsServices productservices2, CartServices cartservices2,
			OrderServices orderservices2, UserServices userservices) {
		this.productservices=productservices2;
		this.orderservices=orderservices2;
		this.userservices=userservices;
		this.cartservices=cartservices2;
	}


	Scanner sc=new Scanner(System.in);
	public void welcomeusermenu(Users currentuser2) {
		currentuser=currentuser2;
		
		    boolean flag = true;

		    while (flag) {

		        try {

		            displaymenu();

		            int option = sc.nextInt();
		            sc.nextLine();

		            switch (option) {

		                case 1: {
		                    productservices.ViewAllProducts();
		                    break;
		                }

		                case 2: {
		                    productservices.SearchProduct();
		                    break;
		                }

		                case 3: {
		                    cartservices.AddProducttoCart(currentuser);
		                    break;
		                }

		                case 4: {
		                    cartservices.ViewCart(currentuser);
		                    break;
		                }

		                case 5: {
		                    cartservices.RemoveProductfromCart(currentuser);
		                    break;
		                }
		                case 6: {
		                	orderservices.CancelOrder(currentuser);
		                    break;
		                }

		                case 7: {
		                    orderservices.placeorder(currentuser);
		                    break;
		                }

		                case 8: {
		                    orderservices.OrderHistory(currentuser);
		                    break;
		                }

		                case 9: {
		                    userservices.MyProfile(currentuser);
		                    break;
		                }

		                case 10: {
		                    flag = false;
		                    break;
		                }

		                default: {
		                    System.out.println("Enter the correct option");
		                }
		            }

		        } catch (Exception e) {

		            g.handler(e);

		        }
		    }
		}
	
	
	
	public void displaymenu(){
		System.out.println();
		System.out.println("================================\r\n"
				+ "        Welcome "+ currentuser.getName()+"\r\n"
				+ "================================\r\n"
				+ "\r\n"
				+ "1. View All Products\r\n"
				+ "2. Search Product\r\n"
				+ "3. Add Product to Cart\r\n"
				+ "4. View Cart\r\n"
				+ "5. Remove Product from Cart\r\n"
				+ "6. Cancel Any Order\r\n"
				+ "7. Place Order\r\n"
				+ "8. Order History\r\n"
				+ "9. My Profile\r\n"
				+ "10. Logout\r\n"
				+ "\r\n"
				+ "Enter Choice:");
	}


}
