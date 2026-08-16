package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable{

	private int cartid;
	private int userid;
	private ArrayList<CartItems> cartitems;
	
	
	
	public ArrayList<CartItems> getCartitems() {
		return cartitems;
	}

	public void setCartitems(ArrayList<CartItems> cartitems) {
		this.cartitems = cartitems;
	}
	
	public Cart(int cartid, int userid, ArrayList<CartItems> cartitems) {
		
		this.cartid = cartid;
		this.userid = userid;
		this.cartitems = cartitems;
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
		return "Cart [cartid=" + cartid + ", userid=" + userid + ", cartitems=" + cartitems + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
