package exceptions;

public class ProductAlreadyExits extends RuntimeException{
	public ProductAlreadyExits(String msg){
		super(msg);
	}
}
