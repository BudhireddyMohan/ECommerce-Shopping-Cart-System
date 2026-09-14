package exceptions;

public class InSuffecentStock extends RuntimeException {
	public InSuffecentStock(String msg) {
		super(msg);
	}
}
