package models;

import java.io.Serializable;

public class Cart implements Serializable{

	private int cartid;
	private int userid;
	
	
	public Cart(int cartid, int userid) {
		
		this.cartid = cartid;
		this.userid = userid;
	}

	public int getCartid() {
		return cartid;
	}
	public void setCartid(int cartid) {
		this.cartid = cartid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}



	@Override
	public String toString() {
		return "Cart [cartid=" + cartid + ", userid=" + userid + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
}
