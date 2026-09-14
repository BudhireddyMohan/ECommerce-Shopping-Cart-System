package DTOs;

public class CartItemDTO {
    
	private String productNmae;
	private int itemprice;
	private int quantity;
	
	
	
	
	public CartItemDTO(String productNmae, int itemprice, int quantity) {
		this.productNmae = productNmae;
		this.itemprice = itemprice;
		this.quantity = quantity;
		
	}
	
	
	
	@Override
	public String toString() {
		return "CartItemDTO [productNmae=" + productNmae + ", itemprice=" + itemprice + ", quantity=" + quantity + "]";
	}
	
	
	public String getProductNmae() {
		return productNmae;
	}
	public void setProductNmae(String productNmae) {
		this.productNmae = productNmae;
	}
	public int getItemprice() {
		return itemprice;
	}
	public void setItemprice(int itemprice) {
		this.itemprice = itemprice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
