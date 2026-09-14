package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable{

	
	private int orderid;
	private int userid;
	private LocalDateTime orderdate;
	
	private OrderStatus status;
	
	
	

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public LocalDateTime getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(LocalDateTime orderdate) {
		this.orderdate = orderdate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Order(int orderid, int userid, LocalDateTime orderdate, OrderStatus status) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.orderdate = orderdate;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", userid=" + userid + ", orderdate=" + orderdate + ", status=" + status
				+ "]";
	}
	
	
	
	
	
	
	
	

	
	
	
}
