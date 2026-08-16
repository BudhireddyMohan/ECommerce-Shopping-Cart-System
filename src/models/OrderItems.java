package models;

import java.io.Serializable;

public class OrderItems implements Serializable{
	
	
	
   private String productname;
   private int price;
   private int quantity;
   
   
   
   
   public String getProductname() {
	return productname;
}
public void setProductname(String productname) {
	this.productname = productname;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public int getQuantity() {
	return quantity;
}
public int TotalAmount() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public OrderItems(String productname, int price, int quantity) {
	super();
	this.productname = productname;
	this.price = price;
	this.quantity = quantity;
}
@Override
public String toString() {
	return "OrderItems [productname=" + productname + ", price=" + price + ", quantity=" + quantity + "]";
}
   
}
