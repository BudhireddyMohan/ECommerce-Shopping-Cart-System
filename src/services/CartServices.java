package services;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

import DTOs.CartItemDTO;
import DTOs.CartProductDTO;
import Repository.DAOs.CartDAO;
import Repository.DAOs.ProductsDAO;
import Utlity.DatabaseTest;
import exceptions.InCorrectProduct;
import exceptions.InSuffecentStock;
import models.Cart;
import models.Products;
import models.Users;

public class CartServices{
		
   
   
   
   //---------------------------------------------objects 
   
   
   Scanner sc=new Scanner(System.in);
   
   ProductsServices productsservices;
   OrderServices orderservices;
   ProductsDAO productsDAo;
   CartDAO cartDao;
   Users currentuser; 
 
   DatabaseTest database;

   
   public CartServices(ProductsServices productservice,ProductsDAO productsDAo,CartDAO cartDao, DatabaseTest database) {
  
	this.productsservices=productservice;
	this.productsDAo=productsDAo;
	this.cartDao=cartDao;
	this.database= database;
	
}


//-------------------  AddProducttoCart


public void AddProducttoCart(Users currentuser) throws SQLException,InCorrectProduct,InSuffecentStock {
	   this.currentuser=currentuser;
	   productsservices.ViewAllProducts();
	   System.out.println("==============================\r\n"
	   		+ "       Add to Cart\r\n"
	   		+ "==============================\r\n"
	   		+ "\r\n"
	   		+ "Enter Product ID:\r\n");
	      int proid=Integer.parseInt(sc.nextLine());
	      
	      //chech weather product contain in products table or not else throw exception
	      Products product= productsservices.GetProduct(proid);
	      
	      
	      System.out.println(product.toString());
	      System.out.println("enter the Quantity");
	      
	      int quantity=Integer.parseInt(sc.nextLine());
	
	      while(quantity<=0 || quantity> product.getStock() ) {
	    	  System.out.println("enter the Vaild Quantity");
	    	  quantity=Integer.parseInt(sc.nextLine());
	  
	      }
	      
	      
	      //checking the current user exits in carttable or not
	      
	     // Optional<Cart> CurrentUserHaveCartOrNot=GetCart(currentuser.getUserid());
	      Optional<List<CartProductDTO>> cartitems= cartDao.GetCartProduct(currentuser.getUserid());
      
	      // if user_id does not contain IN Cart TABLE 
	      
	      
	      Connection con=null;
	       try{
	    	    con= database.getconnection();
	 	     
	    	   con.setAutoCommit(false);
	    	   
	    	   
	    	   if(cartitems.isEmpty()) {  //if user did not have cart 
	 	    	  
	  	    	 
	  	    	 Optional<Cart> crtnewcart= cartDao.Save(con,generateid(),currentuser.getUserid());
	     	 
	  	    	 cartDao.save(con,generateid(),crtnewcart.get().getCartid(),quantity,proid);
	  	    	 con.commit();
	  	    	
	  	    	   System.out.println("============= created the cart and added the product to cart sucessfully"); 
	  	    	   return;
	  	      }else {
	  	    	  
	  	    	  // if user contain cart
	  	    	  
	  	    	  
	  	    	 // get cartitems check where the user enter product id is in cart or
	  	    	  //not if countain increase quantity not contain add the item to acart 
	  	    	  	    	  	    	  
	  	    	  
	  	    	
	  	    	  
	  	    	 for(CartProductDTO ele:cartitems.get()) {
	  	    		 if(ele.getCartitemproductid()  ==proid) {
	  	    			
	  	    			 if(ele.getProductstock()  <ele.getCartitemsQuantity() +quantity) {
	  	    				 throw new InSuffecentStock("your are adding the more stock to your cart > than product cart");
	  	    			 }
	  	    		      
	  	    			 cartDao.updatequantity(con,ele.getCartid(),proid, ele.getCartitemsQuantity() +quantity); 
	  	    			 System.out.println("========Updated the quantity in Cart======");
	  	    			 con.commit();
	  	    			 return;
	  	    		 }
	  	    	 }
	  	    	 
	  	    	 
	  	    	 
	  	    	 cartDao.save(con,generateid(),cartitems.get().get(0).getCartid()  ,quantity,proid);
	  	    	 con.commit();
	  	    	 System.out.println("========Added New Product to Existing  Cart======");
	  	    	  
	  	 
	  	    	  }
	       }catch(Exception e) {
	    	   if(con!=null) {
	    		   con.rollback();
	    	   }
	    	   
	    	 
	    	 throw e;
	       }
	       finally {
	    	   
	    	   if(con!=null) {
	    		   con.setAutoCommit(true); 
	    	   }
	    	  
	    	   con.close();
	       }
	   
	      }
	           
   




   //------------------------ViewCart
   public void ViewCart(Users currentuser) throws SQLException, InCorrectProduct {
	   this.currentuser=currentuser;
	    int TotalAmount = 0;
	    
	    //MySQL Joins
	    Optional<List<CartItemDTO>>  cartitems=cartDao.GetCartItems(currentuser.getUserid());
        
        if(cartitems.isEmpty()) {
        	System.out.println("cart is empty !"); return ;
        }
              
	            System.out.println("========================================");
	            System.out.println("             YOUR CART                  ");
	            System.out.println("========================================");
	            System.out.printf("%-13s %-11s %-8s %s\n", "Product", "Price", "Qty", "Subtotal");
	            System.out.println("------------------------------------------------");
            
	            for (CartItemDTO e : cartitems.get()) {
	                int itemPrice = e.getItemprice();
	                int itemQuantity = e.getQuantity();
	                int subTotal = itemQuantity * e.getItemprice();
	                System.out.printf("%-13s ₹%-10d %-8d ₹%d\n", 
	                e.getProductNmae(), itemPrice, itemQuantity, subTotal);
	                
	                TotalAmount += subTotal;
	            }
	            

	            System.out.println("------------------------------------------------");
	            System.out.printf("%-34s ₹%d\n", "TOTAL", TotalAmount);
	            System.out.println("========================================");
	    
	}
   

   //-------------------------RemovingCartFromUser
   public void Deletecart(Connection con,int userid) throws SQLException {

	   cartDao.DeleteByUserId(con,userid);
   }
  
   
   
   
  //-------------------------------Remove Product from Cartitems
   public void RemoveProductfromCart(Users currentuser) throws SQLException,InCorrectProduct{
	   System.out.println("======================================");
	   System.out.println("Remove Product from Cart");
	   
	  
	//   Optional<Cart> cart=GetCart(currentuser.getUserid());
	   Optional<List<CartProductDTO>> cartproducts=cartDao.GetCartProduct(currentuser.getUserid());
	   
	   if(cartproducts.isEmpty()  || cartproducts.get().isEmpty()) {
		   System.out.println("==============Your Cart Is Empty==========");
		   System.out.println("user not have the cart to deleted the product");
		   return ;}
	   
	  // Optional<List<CartItems>> cartitems=GetCartItems(cartproducts.get().getCartid());

	   System.out.println("================= Your cart =================");
	   
	   
	   for(CartProductDTO ele: cartproducts.get()) {
		   System.out.println("Product Name : "+productsDAo.GetProduct(ele.getCartitemproductid()).get().getProductname());
		   System.out.println("Product id : "+ele.getCartitemproductid() );
		   System.out.println("Product Quantity : "+ ele.getCartitemsQuantity() );
		   System.out.println();
		   System.out.println("--------------------------------------");
		   System.out.println();
	   }
	   
	   
	   System.out.println("enter product id :");
	   
	   int proid=Integer.parseInt( sc.nextLine());
	   
	   Supplier<Optional<CartProductDTO>> cartproduct = () -> {
		    for (CartProductDTO ele : cartproducts.get()) {
		        if (ele.getCartitemproductid() == proid) {
		            return Optional.of(ele); 
		        }
		    }
		    return Optional.empty();
		};
		
		
		if(cartproduct.get().isEmpty()) {
			 System.out.println("======================================");
			    System.out.println("   entered incorrect product id check it once");
			    System.out.println("========================================");
			    throw new InCorrectProduct("entered incorrect product id check it once");
		}
		
		Connection con=null;
		try {
			con=database.getconnection();
			con.setAutoCommit(false);
			 if (cartproduct.get().isPresent()) {
				   
				   cartDao.DeleteProductFromCart(con,cartproduct.get().get().getCartid(), proid);
				  
				    if (cartproducts.get().size() == 1) {
				        cartDao.DeleteCart(con,cartproduct.get().get().getCartid());
				       
				    }
				    con.commit();
				    System.out.println("======================================");
				    System.out.println("    Product Removed Successfully");
				    System.out.println("========================================");
				} 
		}catch(Exception e) {
			if(con!=null) {
				con.rollback();	
			}
			
			throw e;
		}finally{
			if(con!=null) {
				con.setAutoCommit(true);
			}
				
			con.close();
		}
	
	  
   }
   
    
  
  
   
 //-----------------  generateid
   public int generateid() {
	   SecureRandom random = new SecureRandom();
	    return random.nextInt(Integer.MAX_VALUE);
	}
   
   
  
   public void DeleteCart_Items(Connection con,int cartid,int productid) throws SQLException {
	   cartDao.DeleteByCartIdByProductId(con, cartid, productid);
   }
     
}
