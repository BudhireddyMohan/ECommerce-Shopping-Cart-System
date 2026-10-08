package exceptions;

public class GlobelExceptionHandler {

	
	public void handler(Exception e) {
				
		System.out.println("Global handler: " + e.getMessage());
		e.printStackTrace();
		
	}
	}









