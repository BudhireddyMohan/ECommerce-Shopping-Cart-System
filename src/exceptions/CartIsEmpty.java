package exceptions;

public class CartIsEmpty extends RuntimeException{
	public CartIsEmpty(String msg){
		super(msg);
	}
}
