package services;

import java.util.ArrayList;
import java.util.Scanner;

import fileHandlind.FiledataHandling;
import models.Cart;
import models.CartItems;
import models.Products;
import models.Users;

public class CartServices{
		
   	
   ArrayList<Cart> cartcollection=new ArrayList<>();
   
   
   //------------objects 
   Scanner sc=new Scanner(System.in);
   FiledataHandling filehandling;
   ProductsServices productsservices;
   OrderServices orderservices;
    Users currentuser; 
  
  
public CartServices(FiledataHandling filehandling2,ProductsServices productservice) {
	this.filehandling=filehandling2;
	   this.cartcollection= filehandling.loaddata("Cart.dta",cartcollection );   

	this.productsservices=productservice;
}



public void AddProducttoCart(Users currentuser) {
	   this.currentuser=currentuser;
	   productsservices.ViewAllProducts();
	   System.out.println("==============================\r\n"
	   		+ "       Add to Cart\r\n"
	   		+ "==============================\r\n"
	   		+ "\r\n"
	   		+ "Enter Product ID:\r\n");
	      int proid=sc.nextInt();
	      sc.nextLine();
	      Products product;
	      product= productsservices.DoesProductsContaInProductid(proid);
	      if(product==null) {
				System.out.println("Product with ID "+proid+ " not found.");
				return ;
		  }
	      
	      System.out.println(product.toString());
	      System.out.println("enter the Quantity");
	      int quantity=sc.nextInt();
	      sc.nextLine();
	      
	      while(quantity<=0 || quantity> product.getStock() ) {
	    	  System.out.println("enter the Vaild Quantity");
	    	  quantity=sc.nextInt();
		      sc.nextLine();
	      }
	      Cart CurrentUserHaveCartOrNot=UserExitInCartCollection(currentuser);  //cart in collection
	      Cart newcart;
	      
	      
	      // if user does not contain in the ArrayList in cartcollection
	      if(CurrentUserHaveCartOrNot==null) {
	    	  CartItems newitem=new CartItems(proid,quantity);
	    	  ArrayList<CartItems> a=new ArrayList<>();
	    	  a.add(newitem);
	    	   newcart=new Cart(generateid(),currentuser.getUserid(),a);
	    	   cartcollection.add(newcart);
	    	   filehandling.savedata("Cart.dta",cartcollection);
	    	   System.out.println("added to cart sucessfully"); 
	    	   return;
	      }else {
	    	  
	    	  int indexOfCart=-1;
	    	  for(int i=0;i<cartcollection.size();i++) {
	    		  if(cartcollection.get(i).getUserid()==currentuser.getUserid()) {
	    			  indexOfCart=i; break;
	    		  }
	    		  
	    	  }
	    	  
	    	  
	    	  int newquantity;
	    		  for(int j=0;j<cartcollection.get(indexOfCart).getCartitems().size();j++) {
	    			  if(cartcollection.get(indexOfCart).getCartitems().get(j).getProductid()==proid) {
	    				   newquantity=cartcollection.get(indexOfCart).getCartitems().get(j).getQuantity()+quantity;
	    				  int productstock=productsservices.GetProductStock(proid);
	    				  if(newquantity>productstock) {
	    					  System.out.println("stock not Avalible");
	    					  return;
	    				  }else {
	    					  cartcollection.get(indexOfCart).getCartitems().get(j).setQuantity(newquantity);  
		    				  filehandling.savedata("Cart.dta",cartcollection);
		    				  System.out.println("added to cart sucessfully"); 
		    				  return ; 
	    				  }  
	    			  }
	    		  }
	    		//Cart newcart1=new Cart(quantity, quantity, null);
		    	  CartItems itm=new CartItems(proid,quantity);
		    	  cartcollection.get(indexOfCart).getCartitems().add(itm); 
		    	  filehandling.savedata("Cart.dta",cartcollection);
				  System.out.println("added to cart sucessfully");
	    	  }
	      }
	      
	      //if cart exits in collectioncart
	      // check if the cart exits the product or not with proid(userentered)	      
   
   
   
   
   //------------------------ViewCart
   public void ViewCart(Users currentuser) {
	   this.currentuser=currentuser;
	    int TotalAmount = 0;
	    Cart usercart;
	    
	    for (Cart ele : cartcollection) {
	        if (ele.getUserid() == currentuser.getUserid() && ele.getCartitems().size()!=0) {
	            usercart=ele;
	            
	            System.out.println("========================================");
	            System.out.println("             YOUR CART                  ");
	            System.out.println("========================================");
	            System.out.printf("%-13s %-11s %-8s %s\n", "Product", "Price", "Qty", "Subtotal");
	            System.out.println("------------------------------------------------");

	            for (CartItems e : ele.getCartitems()) {
	                Products product = productsservices.DoesProductsContaInProductid(e.getProductid());
	                int itemPrice = product.getPrice();
	                int itemQuantity = e.getQuantity();
	                int subTotal = itemQuantity * productsservices.getproductprice(e.getProductid());
	                System.out.printf("%-13s ₹%-10d %-8d ₹%d\n", 
	                    product.getProductname(), itemPrice, itemQuantity, subTotal);
	                
	                TotalAmount += subTotal;
	            }
	            if(TotalAmount==0) {
	            	return;
	            }

	            System.out.println("------------------------------------------------");
	            System.out.printf("%-34s ₹%d\n", "TOTAL", TotalAmount);
	            System.out.println("========================================");
	            
	            
	            return;
	        }
	    }
	    
	    System.out.println("Your Cart Is Empty");
	    return;
	}
   
   
   
   
   
   //--------------------------placeorder
   
//   public void placeorder(Users currentuser ) {
//	   orderservices.placeorder(UserExitInCartCollection(currentuser));
//   }
   
   //-------------------------RemovingCartFromUser
   public void RemovingCartFromUser(int cartid) {
	   for(Cart ele:cartcollection) {
		   if(ele.getCartid()==cartid) {
			   cartcollection.remove(ele);  
			   filehandling.savedata("Cart.dta",cartcollection);
			   return;
		   }
	   }
   }
  
   
   
   
  //-------------------------------Remove Product from Cartitems
   public void RemoveProductfromCart(Users currentuser) {
	   
	   System.out.println("Remove Product from Cart");
	   
	   Cart cart=UserExitInCartCollection(currentuser);
	   
	   System.out.println(" Your cart :"+cart);
	   System.out.println("enter product id :");
	   
	   int proid=sc.nextInt();
	   sc.nextLine();
	   ArrayList<CartItems> newcartitems=new ArrayList<>();
	   for(CartItems i: cart.getCartitems()) {
		   if(i.getProductid()!=proid) newcartitems.add(i); 
	   }
	   cart.setCartitems(newcartitems);
	   for(int i=0;i< cartcollection.size();i++) {
		   if(cartcollection.get(i).getUserid()==currentuser.getUserid()) {
			   cartcollection.set(i, cart);
			   filehandling.savedata("Cart.dta",cartcollection);
			   break;
		   }} 
	   
	   System.out.println("======================================");
	   System.out.println("    Product Removed SucessFully");
	   System.out.println("========================================");
	   
   }
   
   


   
   
   
 //----------------------UserExitInCartCollection  
   public Cart UserExitInCartCollection(Users currentuser) {
	   for(Cart ele:cartcollection) {
	    	  if(ele.getUserid()==currentuser.getUserid()) {
	    		  return ele;
	    	  }
	      }
	   return null;
   }
   
   
 //-----------------  generateid
   public int generateid() {
		if(cartcollection.size()==0) return 1;
		return cartcollection.get(cartcollection.size()-1).getCartid() +1;
	}
   
  
   
}
