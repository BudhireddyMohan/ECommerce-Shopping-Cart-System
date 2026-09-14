package DTOs;

public class CartProductDTO {
  
	
	private int cartitemproductid;
	
	private int productstock;
	private int cartitemsQuantity;
	private int cartid;
	
	
	
	
	
	public int getCartid() {
		return cartid;
	}
	public void setCartid(int cartid) {
		this.cartid = cartid;
	}
	public int getProductstock() {
		return productstock;
	}
	public CartProductDTO(int cartitemproductid, int productstock, int cartitemsQuantity, int cartid) {
		super();
		this.cartitemproductid = cartitemproductid;
		this.productstock = productstock;
		this.cartitemsQuantity = cartitemsQuantity;
		this.cartid = cartid;
	}
	public void setProductstock(int productstock) {
		this.productstock = productstock;
	}
	public int getCartitemproductid() {
		return cartitemproductid;
	}
	public void setCartitemproductid(int cartitemproductid) {
		this.cartitemproductid = cartitemproductid;
	}
	public int getCartitemsQuantity() {
		return cartitemsQuantity;
	}
	public void setCartitemsQuantity(int cartitemsQuantity) {
		this.cartitemsQuantity = cartitemsQuantity;
	}
	@Override
	public String toString() {
		return "CartProductDTO [cartitemproductid=" + cartitemproductid + ", productstock=" + productstock
				+ ", cartitemsQuantity=" + cartitemsQuantity + ", cartid=" + cartid + "]";
	}
	

	
	
	
}
