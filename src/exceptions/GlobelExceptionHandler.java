package exceptions;

public class GlobelExceptionHandler {

	
	public void handler(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		
	}
	
	
	
}
