package services;

public class EmailService {
   
	public void sendemail(String email,String message) {
		try {
			
		System.out.println("sending email");
		
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		
		System.out.println("email sended to "+email+" "+ message);
		
	}

	
	public void SendRedAlertStockIsLow(String email,int productid,int stock) {
		try {
			
			System.out.println(" Sending Red Alert Email ");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Dear "+email+ "the product"+productid+"stock "+ stock+"is Low");
	}
	
}
