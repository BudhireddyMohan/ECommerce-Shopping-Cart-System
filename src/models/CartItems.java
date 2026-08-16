package models;

import java.io.Serializable;

public class CartItems implements Serializable {
	
	
   private int  productid;
   private int quantity;
   
   
   
   
public CartItems(int productid, int quantity) {
	this.productid = productid;
	this.quantity = quantity;

}



@Override
public String toString() {
	return "CartItems [productid=" + productid + ", quantity=" + quantity +  "]";
}



public int getProductid() {
	return productid;
}
public void setProductid(int productid) {
	this.productid = productid;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}

   
   
}
