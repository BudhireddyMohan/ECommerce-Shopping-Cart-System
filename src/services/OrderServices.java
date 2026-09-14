package services;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import DTOs.AddToCartDto;
import DTOs.OrderItemsDTOs;
import Repository.DAOs.OrdersDAO;
import Utlity.DatabaseTest;
import exceptions.CartIsEmpty;
import exceptions.DataBaseConnection;
import exceptions.GlobelExceptionHandler;
import exceptions.InCorrectProduct;
import exceptions.InSuffecentStock;
import models.OrderStatus;
import models.Users;

public class OrderServices {

	
	LocalDate today = LocalDate.now();
	 
	
	
	
	//------------------objects

	ProductsServices productservices;
	CartServices cartservices;
	GlobelExceptionHandler exceptionhandler;
	Scanner sc=new Scanner(System.in);
	Users currentuser;
	OrdersDAO ordersdao;
	DatabaseTest database;
	//Order_itemsDAO order_itemsdao;
	
	public OrderServices(ProductsServices productservice, CartServices cartservices,GlobelExceptionHandler exceptionhandler,OrdersDAO ordersdao,DatabaseTest database) {
		this.productservices=productservice;
		this.cartservices=cartservices;
	
	
		this.exceptionhandler=exceptionhandler;
		//this.order_itemsdao=order_itemsdao;
		this.ordersdao=ordersdao;
		}
	
	
	
//------------------------------------placeorder



	public void placeorder(Users currentuser) throws SQLException, InCorrectProduct,CartIsEmpty,InSuffecentStock,DataBaseConnection{
		System.out.println("==============Place order===============");
		
		// show cartItems To User
		cartservices.ViewCart(currentuser);
		
		
		
		
		Optional<List<AddToCartDto>> cartitems=ordersdao.Addtocart(currentuser.getUserid());
		
		if(cartitems.isEmpty()) {System.out.println("To Place Order ! OOPs");return;}
		

		
		//checking the stock is valid in products stock and cart stock
		
		for(AddToCartDto ele: cartitems.get()) {
			
		
			if(ele.ciquantity()>ele.p_stock()) {
				throw new InSuffecentStock("Stock is Insuffent to Place The Order");
			}
		}
		
		
		
		

		System.out.println("Are U Sure To Place Order ?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		int option= Integer.parseInt(sc.nextLine());
	
		while(option!=1 && option!=2) {
			System.out.println("enter correct option : ");
			 option= Integer.parseInt(sc.nextLine());
		}
		
		if(option==2) return;
		
		int order_id=generateid();
		
		Connection con=null;
		try {
		    con=database.getconnection();
		    if(con==null) throw new DataBaseConnection("data base connection error");
			con.setAutoCommit(false);
			//Adding the Order in the Orders Table
			ordersdao.save(con,order_id,currentuser.getUserid(),LocalDateTime.now(),OrderStatus.PENDING);
		
			
			for(AddToCartDto ele:cartitems.get()) {
				
			
				//saving to order items
				ordersdao.save(con,generateid(),order_id,ele.p_productname() ,ele.p_price() ,ele.ciquantity() );
				
				//updating the stock in products
				productservices.updatestock(con,ele.ciproductid() , ele.p_stock()  -ele.ciquantity() );
				
				// Without Deleing the Cartitems we can not delete the cart because of forigen key, for this line (cartservices.Deletecart(currentuser.getUserid());)
				cartservices.DeleteCart_Items(con,ele.cartid()  ,ele.ciproductid() );	
			}
			
			// After the user ordered the cart will deleted
			cartservices.Deletecart(con,currentuser.getUserid());
			
			
			
			
			System.out.println("===============================================");
			System.out.println("           Order Placed Sucessfully !           ");
			System.out.println("=================================================");	
					
		}catch(Exception e) {
			if(con !=null) {
				con.rollback();
			}
			throw e;
		}finally{
			try {
				if(con!=null) {
					con.setAutoCommit(true);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			con.close();
		}
		
		
	}
	
	
	
	
	//---------------------------Order History
	public void OrderHistory(Users currentuser) throws SQLException {
	    this.currentuser = currentuser;

	    System.out.println();
	    System.out.println("======================================================");
	    System.out.println("                       ORDER HISTORY");
	    System.out.println("======================================================");
  
	    Optional<List<OrderItemsDTOs>> orders=ordersdao.GetOrderItemsByOrderId(currentuser.getUserid());
	    if(orders.isEmpty()) {
	    	System.out.println("========== No Orders ===========");
	    	return;
	    }
    
	    for (OrderItemsDTOs e : orders.get()) {
	            System.out.println();
	            System.out.println("--------------------------------------");
	            System.out.println(" Order ID      : " + e.getOrderid());
	            System.out.println(" Order Status  : " + e.getStatus());
	            System.out.println("--------------------------------------");
	            System.out.println(" Product Name  : " + e.getProductname());
	            System.out.println(" Price         : ₹" + e.getPrice());
	            System.out.println(" Quantity      : " + e.getQuantity());  
	            System.out.println("-----------------------------------");
	            System.out.println("Total Amount : "+e.getPrice()* e.getQuantity());
	    }
        
	    System.out.println("=======================================================");
	    System.out.println("                    END OF ORDER HISTORY");
	    System.out.println("======================================================");
	}
	
	
	
	
	
	
	
	
	
	
//---------------------------ViewAllOrders  (Admin operation)
	
	
	public void ViewAllOrders() throws SQLException {
		
		 int TotalAmount=0;
		
		   Optional<List<OrderItemsDTOs>> orders=ordersdao.GetAllOrders();
		   
		    if(orders.isEmpty()) {
		    	System.out.println("========== No Orders ===========");
		    	return;
		    }
		    
		    
		    for (OrderItemsDTOs e : orders.get()) {
		            System.out.println();
		            System.out.println("--------------------------------------");
		            System.out.println(" Order ID      : " + e.getOrderid());
		            System.out.println(" Order Status  : " + e.getStatus());
		            System.out.println("--------------------------------------");
		                System.out.println(" Product Name  : " + e.getProductname());
		                System.out.println(" Price         : ₹" + e.getPrice());
		                System.out.println(" Quantity      : " + e.getQuantity());  
		                System.out.println("-----------------------------------");
		                System.out.println("Total Amount : "+e.getPrice()* e.getQuantity());
		                System.out.println("----------------------------------------");
						TotalAmount+=e.getQuantity()*e.getPrice();
					System.out.println("--------------------------------------------------");
		            
		        
		    }
		System.out.println("----------------------------------------");
	    System.out.println("Total Amount : "+ TotalAmount);
	}
	

	
	
	//------------------------------------------ViewOrderdetails  joins
	public void ViewOrderdetails() throws SQLException {
		System.out.println("==============================");
		System.out.println("       Order Details "     );
		System.out.println("===============================");
		ViewAllOrders();
		System.out.println("enter the order id : ");
		
		int orderid= Integer.parseInt(sc.nextLine());	
		int TotalAmount=0;	
		Optional<List<OrderItemsDTOs>> ordersitems= ordersdao.GetOrderItemsByOrderId(orderid);
		if(ordersitems.isEmpty()) {
			System.out.println("In correct Orderid ");
			return;
		}
		
		 for (OrderItemsDTOs e : ordersitems.get()) {
	            System.out.println();
	            System.out.println("--------------------------------------");
	            System.out.println(" Order ID      : " + e.getOrderid());
	            System.out.println(" Order Status  : " + e.getStatus());
	            System.out.println("--------------------------------------");

	           

	                System.out.println(" Product Name  : " + e.getProductname());
	                System.out.println(" Price         : ₹" + e.getPrice());
	                System.out.println(" Quantity      : " + e.getQuantity());  
	                System.out.println("-----------------------------------");
	                System.out.println("Total Amount : "+e.getPrice()* e.getQuantity());
	                System.out.println("----------------------------------------");
					TotalAmount+=e.getQuantity()*e.getPrice();
				System.out.println("--------------------------------------------------");
	            
	        
	    }
	System.out.println("----------------------------------------");
 System.out.println("Total Amount : "+ TotalAmount);

				
	}
	
	
	
	//--------------------------------Develery All Orders
	
	
	
	public void DeveleryAllOrdersPendingOrders() throws SQLException{
		
		
		Optional<List<OrderItemsDTOs>> orders=ordersdao.GetOrdersByStatus(OrderStatus.PENDING);
		
		if(orders.isEmpty()) {System.out.println("No Pending Orders "); return ;}
		
		for(OrderItemsDTOs ele: orders.get()) {
			ordersdao.UpdateOrderStatus(ele.getOrderid());
		}
		
	}
	
	
	
	
	
	//----------------------------------------viewAllOrdersByStatus
	public void viewAllOrdersByStatus() throws SQLException {
		
		
		System.out.println("1 - PENDING");
		System.out.println("2 - DELIVERED");
		System.out.println("3 - SHIPPED");
		System.out.println("4 - CONFIRMED");
		OrderStatus status = null;
		System.out.println("enter your Option : ");
		int option=Integer.parseInt(sc.nextLine());
		
		switch(option) {
		case 1: {
			status=OrderStatus.PENDING;
			break;
		}
		case 2: {
			status=OrderStatus.DELIVERED;
			break;
		}case 3:{
			status=OrderStatus.SHIPPED;
			break;
		}
		case 4:{
			status=OrderStatus.CONFIRMED;
			break;
		}
		default :{
			System.out.println("enter In Valid Option");
		}
		}
		
		Optional<List<OrderItemsDTOs>> orderitems=ordersdao.GetOrdersByStatus(status);
		int TotalAmount=0;
		
		
		
		if(orderitems.isEmpty()) {
			System.out.println(" No Pending Oders ! ");
			return;
		}
		
		 for (OrderItemsDTOs e : orderitems.get()) {
	            System.out.println();
	            System.out.println("--------------------------------------");
	            System.out.println(" Order ID      : " + e.getOrderid());
	            System.out.println(" Order Status  : " + e.getStatus());
	            System.out.println("--------------------------------------");
	                System.out.println(" Product Name  : " + e.getProductname());
	                System.out.println(" Price         : ₹" + e.getPrice());
	                System.out.println(" Quantity      : " + e.getQuantity());  
	                System.out.println("-----------------------------------");
	                System.out.println("Total Amount : "+e.getPrice()* e.getQuantity());
	                System.out.println("----------------------------------------");
					TotalAmount+=e.getQuantity()*e.getPrice();
				System.out.println("--------------------------------------------------");
	            
	        
	    }
	System.out.println("----------------------------------------");
 System.out.println("Total Amount : "+ TotalAmount);
	
	}

	
	
	
	//-------------------------generateid
	public int generateid() {
		 SecureRandom random = new SecureRandom();
		    return random.nextInt(Integer.MAX_VALUE);
	}
	
}
