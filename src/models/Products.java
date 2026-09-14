package models;

import java.io.Serializable;

public class Products implements Serializable {

	
	private int productid;
	private String productname;
	private String category;
	private int price;
	private int stock;
	
	
	
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	public Products(int productid, String productname, String category, int price, int stock) {
	
		this.productid = productid;
		this.productname = productname;
		this.category = category;
		this.price = price;
		this.stock = stock;
	}
	
	
	@Override
	public String toString() {
		return "Products [productid=" + productid + ", productname=" + productname + ", category=" + category
				+ ", price=" + price + ", stock=" + stock + "]";
	}
	
	
	
}
