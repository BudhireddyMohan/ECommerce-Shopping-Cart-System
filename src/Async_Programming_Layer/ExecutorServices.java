package Async_Programming_Layer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class ExecutorServices {
	
	private ExecutorService newthreadservice;
	
	
	public ExecutorServices(){
		
		this.newthreadservice=Executors.newFixedThreadPool(1);
		
	}
	
	
	
	public ExecutorService getNewthreadservice() {
		return newthreadservice;
	}
	
	
	public void ereasethread() {
		this.newthreadservice.shutdown();
	}
	
}
