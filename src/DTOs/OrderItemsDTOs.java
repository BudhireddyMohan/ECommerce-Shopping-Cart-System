package DTOs;

import models.OrderStatus;

public class OrderItemsDTOs {

	private int orderid;
	private OrderStatus status;
	private String productname;
	private int price;
	private int quantity;
	private int productid;
	
	
	
	
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
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
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public OrderItemsDTOs(int orderid, OrderStatus status, String productname, int price, int quantity) {
		super();
		this.orderid = orderid;
		this.status = status;
		this.productname = productname;
		this.price = price;
		this.quantity = quantity;
	
	}
	@Override
	public String toString() {
		return "OrderItemsDTOs [orderid=" + orderid + ", status=" + status + ", productname=" + productname + ", price="
				+ price + ", quantity=" + quantity +  "]";
	}

	
	
	
	
	
}
