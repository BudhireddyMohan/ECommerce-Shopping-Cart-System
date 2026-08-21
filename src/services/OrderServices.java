package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.CartIsEmpty;
import exceptions.GlobelExceptionHandler;
import fileHandlind.FiledataHandling;
import models.Cart;
import models.CartItems;
import models.Order;
import models.OrderItems;
import models.OrderStatus;
import models.Products;
import models.Users;

public class OrderServices {

	
	LocalDate today = LocalDate.now();
	 
	ArrayList<Order> orderscollection=new ArrayList<>();
	
	
	//------------------objects
	FiledataHandling filehandling;
	ProductsServices productservices;
	CartServices cartservices;
	GlobelExceptionHandler exceptionhandler;
	Scanner sc=new Scanner(System.in);
	Users currentuser;
	
	public OrderServices(FiledataHandling filehandling2,ProductsServices productservice, CartServices cartservices2,GlobelExceptionHandler exceptionhandler) {
		this.productservices=productservice;
		this.cartservices=cartservices2;
		this.filehandling=filehandling2;
		this.orderscollection=filehandling.loaddata("Order.dta", orderscollection);
		this.exceptionhandler=exceptionhandler;
		}
	
	
	
//------------------------------------placeorder



	public void placeorder(Users currentuser) {
		System.out.println("Place order");
		Cart usercart= cartservices.UserExitInCartCollection(currentuser);
		if(usercart==null) {
			throw new CartIsEmpty("Cart Is Empty To Place Order");
		}
		// checking does the product suffent or not in cart and product stock
		cartservices.ViewCart(currentuser);
		System.out.println("Are U Sure To Place Order ?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		int option=sc.nextInt();
		sc.nextLine();
		while(option!=1 && option!=2) {
			System.out.println("enter correct option : ");
			option =sc.nextInt();
			sc.nextInt();
		}
		
		
		switch(option) {
		case 1:{
			
			
			for(CartItems ele: usercart.getCartitems()) {
				if(ele.getQuantity()>productservices.GetProductStock(ele.getProductid()))
				{
					System.out.println("ele.getQuantity()>productservices.GetProductStock(ele.getProductid()) : "+ ele.getQuantity() +productservices.GetProductStock(ele.getProductid()));
					System.out.println(" Stock not Suffent ");
					return;
				}
			}
						
			ArrayList<OrderItems> a=new ArrayList<>();
			
			for(int i=0;i<usercart.getCartitems().size();i++) {
				Products pro=productservices.DoesProductsContaInProductid(usercart.getCartitems().get(i).getProductid());
				OrderItems orderitem=new OrderItems(pro.getProductname(),pro.getPrice(),usercart.getCartitems().get(i).getQuantity());
				a.add(orderitem);
			}
			
			for(CartItems ele: usercart.getCartitems()) {
				productservices.updatestock(ele.getProductid(),ele.getQuantity());
			}
			
			
			Order order=new Order(generateid(),usercart.getUserid(),today,OrderStatus.ORDERED,a);
			orderscollection.add(order);
			filehandling.savedata("Order.dta", orderscollection);
			
			System.out.println("===============================================");
			System.out.println("           Order Placed Sucessfully !           ");
			System.out.println("=================================================");
			System.out.println("orderscollection : "+ orderscollection);
			
			// remove cart from the user
			
			cartservices.RemovingCartFromUser(usercart.getCartid());
			break;
		}case 2:{
			return;
		}
	
		}
		
		
	}
	
	
	
	
	//---------------------------Order History
	public void OrderHistory(Users currentuser) {
	    this.currentuser = currentuser;

	    System.out.println();
	    System.out.println("======================================================");
	    System.out.println("                       ORDER HISTORY");
	    System.out.println("======================================================");
       //int totalamount = 0;
	    for (Order ele : orderscollection) {

	        if (ele.getUserid() == currentuser.getUserid()) {

	            System.out.println();
	            System.out.println("--------------------------------------");
	            System.out.println(" Order ID      : " + ele.getOrderid());
	            System.out.println(" Order Status  : " + ele.getStatus());
	            System.out.println("--------------------------------------");

	            for (OrderItems e : ele.getOrderitems()) {

	                System.out.println(" Product Name  : " + e.getProductname());
	                System.out.println(" Price         : ₹" + e.getPrice());
	                System.out.println(" Quantity      : " + e.getQuantity());  
	                System.out.println("-----------------------------------");
	                System.out.println("Total Amount : "+e.getPrice()* e.getQuantity());
	            }
	        }
	    }
        
	    System.out.println("=======================================================");
	    System.out.println("                    END OF ORDER HISTORY");
	    System.out.println("======================================================");
	}
	
	
	//---------------------------ViewAllOrders
	public void ViewAllOrders() {
		if(orderscollection.size()==0) {
			System.out.println("=========== No Orders =======");
			return;
		}
		System.out.println("==========================");
		System.out.println("    All Orders ");
		System.out.println("==========================");
         int TotalAmount=0;
		for(Order ele:orderscollection) {
			System.out.println("Order Id : "+ele.getOrderid());
			System.out.println("Order Date :"+ele.getOrderdate());
			for(OrderItems e: ele.getOrderitems()) {
				System.out.println("Product Name : "+e.getProductname()); 
				System.out.println("Quantity  : "+e.getQuantity()); 
				System.out.println("Product price : "+e.getPrice()); 
				System.out.println("------------------------------------------");
				System.out.println("sub Amount : "+e.getQuantity()*e.getPrice());
				System.out.println("----------------------------------------");
				TotalAmount+=e.getQuantity()*e.getPrice();
				System.out.println("--------------------------------------------------");
			}
		}
		System.out.println("----------------------------------------");
		System.out.println("Total Amount : "+ TotalAmount);
	}
	

	
	
	//------------------------------------------ViewOrderdetails
	public void ViewOrderdetails() {
		System.out.println("==============================");
		System.out.println("       Order Details ");
		System.out.println("===============================");
		//ViewAllOrders();
		System.out.println("enter the order id : ");
		int orderid=sc.nextInt();
		sc.nextLine();
		int Totalamount=0;
		for(Order ele:orderscollection) {
			if(ele.getOrderid()==orderid) {
				System.out.println("UserId : "+ele.getUserid());
				System.out.println("Order Date"+ ele.getOrderdate());
				for(OrderItems e: ele.getOrderitems()) {
					System.out.println("Product Nmae : "+e.getProductname());
					System.out.println("Quantity : "+e.getQuantity());
					System.out.println("Price : "+e.getPrice());
					System.out.println("Sub Total : "+e.getQuantity()*e.getPrice());
					System.out.println("-----------------------------------------------------");
					Totalamount+=e.getQuantity()*e.getPrice();
				}
				System.out.println("------------------------------------");
				System.out.println("Total Amount : "+ Totalamount);
				System.out.println("------------------------------------");
				System.out.println("Order status : "+ ele.getStatus());
				return;
			}
		}
				
	}
	
	
	
	//--------------------------------Develery All Orders
	OrderStatus status;
	public void DeveleryAllOrdersPendingOrders() {
		
		boolean nopending=true;
		for(int i=0;i<orderscollection.size();i++) {
			nopending=false;
			if(orderscollection.get(i).getStatus()==OrderStatus.ORDERED) {
			      orderscollection.get(i).setStatus(OrderStatus.DELIVERED);
			}
		}
		
		filehandling.savedata("Order.dta", orderscollection);
		if(nopending) System.out.println("No Pending Orders To Delevery !");
	}
	
	
	
	
	
	//----------------------------------------viewAllPendingOrders
	public void viewAllPendingOrders() {
		boolean nopending=true;
		for(Order ele:orderscollection) {
			if(ele.getStatus().equals(OrderStatus.ORDERED)) {
				nopending=false;
				System.out.println("Order Id : "+ele.getOrderid());
				System.out.println("User Id : "+ele.getUserid());
				System.out.println("Order Date : "+ele.getOrderdate());
				
				for(OrderItems e:ele.getOrderitems()) {
					System.out.println("Product Name : "+e.getProductname());
					System.out.println(" Price : "+e.getPrice());
					System.out.println("Quantity : "+e.getQuantity());
					System.out.println("Sub Total : "+e.getQuantity()*e.getPrice());
				}
			}
		}
		
		if(nopending) System.out.println("No Pending Oders");
	}
	
	
	
	
	
	
	//-------------------------generateid
	public int generateid() {
		if(orderscollection.size()==0) return 0;
		return orderscollection.get(orderscollection.size()-1).getOrderid()+1;
	}
	
}
