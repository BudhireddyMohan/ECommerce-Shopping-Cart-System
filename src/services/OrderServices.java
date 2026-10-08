package services;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Async_Programming_Layer.ExecutorServices;
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
	ExecutorServices serviceexecutor;
	EmailService emailservice;
	
	
	
	public OrderServices(ProductsServices productservice, CartServices cartservices
			,GlobelExceptionHandler exceptionhandler,OrdersDAO ordersdao,DatabaseTest database,ExecutorServices serviceexecutor,EmailService emailservice) {
		this.productservices=productservice;
		this.cartservices=cartservices;
	   this.serviceexecutor=serviceexecutor;
	   this.emailservice=emailservice;
	   this.database=database;
		this.exceptionhandler=exceptionhandler;
		this.ordersdao=ordersdao;
		
		
		}
	
	
	
//------------------------------------placeorder



	public void placeorder(Users currentuser) throws SQLException, InCorrectProduct,CartIsEmpty,InSuffecentStock,DataBaseConnection{
		System.out.println("==============Place order===============");
		
		// show cartItems To User
		cartservices.ViewCart(currentuser);		
		//checking the stock is valid in products stock and cart stock
		
		
		Connection con=null;
		try {
			con=database.getconnection();
			if(con==null) {
				  throw new DataBaseConnection("data base connection error");
			  }
			
			Optional<List<AddToCartDto>> cartitems=ordersdao.Addtocart(con,currentuser.getUserid());

			if(cartitems.isEmpty()) {System.out.println("No Cart To Place Order ! OOPs");return;}
			   synchronized(this) {
					
					for(AddToCartDto ele: cartitems.get()) {
						
						
						//ordersdao.locking_Products_Row(con,ele.p_productid());
                        
						if(ele.ciquantity()>ele.p_stock()) {
							throw new InSuffecentStock("Stock is Insuffent to Place The Order");
						}
						
						if(ele.p_stock()<10) {
                        	serviceexecutor.getNewthreadservice().execute(()->emailservice.SendRedAlertStockIsLow("mohanbudhiress", ele.p_productid(), ele.p_stock()));
						}
							
					}}
					
			System.out.println("Are U Sure To Place Order ?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int option= Integer.parseInt(sc.nextLine());
		
			while(option!=1 && option!=2) {
				System.out.println("enter correct option : ");
				 option= Integer.parseInt(sc.nextLine());
			}
			
			if(option==2) return;
			
			
			con.setAutoCommit(false);
			
			  
		
		   synchronized(this) {
			
			for(AddToCartDto ele: cartitems.get()) {
				
				ordersdao.locking_Products_Row(con,ele.p_productid());
				if(ele.ciquantity()>ele.p_stock()) {
					throw new InSuffecentStock("Just A moment stock is ordered by Another person");
				}
					
			}
		}
	
		int order_id=generateid();
	
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
			
			serviceexecutor.getNewthreadservice().execute(()->emailservice.sendemail("mohanbudhireddy2004@gmail.com","orderPlaced Sucessfully"));
			
			
			
			System.out.println("===============================================");
			System.out.println("           Order Placed Sucessfully !           ");
			System.out.println("=================================================");	
					
		}catch(Exception e) {
			if(!con.isClosed()) {
				con.rollback();
			}
			throw e;
		}finally{
			try {
				if(!con.isClosed()) {
					con.setAutoCommit(true);
					con.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
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

	
	//--------------------Cancel Order
	 public void CancelOrder(Users currentuser) throws SQLException,DataBaseConnection{
		   this.currentuser=currentuser;
		   
		   Optional<List<OrderItemsDTOs>> orderitems=ordersdao.GetOrdersByStatus(OrderStatus.PENDING);
		   if(orderitems.isEmpty()) throw new CartIsEmpty("No Pending orders Yet !");
		   
		   int TotalAmount=0;
		   
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
		   
		   
//		   System.out.println("Enter the Order id to Delete the Order ");
//		   
		  // int orderid;
//		   
		   
		   boolean orderexits=false;
			  System.out.println("Enter correct Orderid :");
			 int   orderid=Integer.parseInt(sc.nextLine());
			 for(OrderItemsDTOs ele: orderitems.get()) {
				   if(ele.getOrderid()==orderid) {
					   orderexits=true;
					   break;
				   }}
		   
		   while(!orderexits) {
			  System.out.println("Enter correct Orderid :");
			   orderid=Integer.parseInt(sc.nextLine());
			   for(OrderItemsDTOs ele1: orderitems.get()) {
				   if(ele1.getOrderid()==orderid) {
					   orderexits=true;
					   break;
				   }
			   }   
		   }
		   
		   
		   Connection con=null;
		   try {
			   
			   con=database.getconnection();
			   if(con==null)  throw new  DataBaseConnection("Failed at connection the database");
			   
			   
			   con.setAutoCommit(false);
 
			   // deleting the order id , it will delete the orderitems internally beacuse cascading
			   ordersdao.DelectByOrderid(con, orderid);
			   con.commit();
			   serviceexecutor.getNewthreadservice().execute(()->emailservice.sendemail("mohanBudhireddy2004@gmail.com","Order Canclled "));
			   
			   
			   
		   }catch(Exception e) {
			   if(!con.isClosed()) {
				   con.rollback();
			   }
			   throw e;
			   
		   }finally {
			   try {
				   if(!con.isClosed()) {
					   con.setAutoCommit(true);
					   con.close();
				   }
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			  
		   }
	   }
	 
	 
	 //---------------------DailyReport
	 
	 public void DailyReport()  {
		 
		serviceexecutor.getNewthreadservice().submit(()->{
			try {
				int total= ordersdao.SalesOfDay();
				System.out.println("total sale = "+total);
			}catch(Exception e) {
				e.printStackTrace();}
		});
		
		
	 }
		
	 
	 
	 
	 
	 
	
	
	//-------------------------generateid
	public int generateid() {
		 SecureRandom random = new SecureRandom();
		    return random.nextInt(Integer.MAX_VALUE);
	}
	
}
