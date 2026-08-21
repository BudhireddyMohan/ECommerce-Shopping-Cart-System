package fileHandlind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class FiledataHandling {
   // File file=new File("Database/Users.dta");
     
    //--------------------load data
    public <T> ArrayList<T>  loaddata(String s,ArrayList<T> type) {
    	File file=new File("Database/"+s);
    	try(FileInputStream fis=new FileInputStream(file);ObjectInputStream ois=new ObjectInputStream(fis);)
    	{
    		@SuppressWarnings("unchecked")
			ArrayList<T> result=(ArrayList<T>)ois.readObject();
    		return result;
    	}catch(Exception e) {
    		
    		System.out.println("exception at loaddata() method"+"error At filename -> "+ s +"->"+e);
    	}
    	return new ArrayList<>();
    	
    }
       
    
    //---------------------save data
    public <T> void savedata(String s,ArrayList<T> type){
    	
    	File file=new File("Database/"+s);
    	try(FileOutputStream fos=new FileOutputStream(file); ObjectOutputStream oos=new ObjectOutputStream(fos)){
    		oos.writeObject(type);
    		
    	}catch(Exception e) {
    		System.out.println("exception at saving data savedata() method"+"error At filename"+s+e);
    	}
    }
}
