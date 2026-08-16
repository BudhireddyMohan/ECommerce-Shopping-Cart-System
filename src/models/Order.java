package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable{

	
	private int orderid;
	private int userid;
	private LocalDate orderdate;
	
	private OrderStatus status;
	private ArrayList<OrderItems> orderitems;
	
	
	
	
	
	public void setOrderdate(LocalDate orderdate) {
		this.orderdate = orderdate;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getUserid() {
		return userid;
	}
	public LocalDate getOrderdate() {
		return orderdate;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public ArrayList<OrderItems> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(ArrayList<OrderItems> orderitems) {
		this.orderitems = orderitems;
	}
	
	public Order(int orderid, int userid, LocalDate orderdate, OrderStatus status, ArrayList<OrderItems> orderitems) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.orderdate = orderdate;
		this.status = status;
		this.orderitems = orderitems;
	}
	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", userid=" + userid + ", orderdate=" + orderdate + ", status=" + status
				+ ", orderitems=" + orderitems + "]";
	}
	
	
	
}
