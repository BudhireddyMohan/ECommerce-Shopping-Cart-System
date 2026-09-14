package Repository.DAOs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import models.Users;

public interface UserDAO  {
	
	
	public void saveregisterdata(Users user) throws SQLException;
	
	public Optional<Users> logincheck(String username,String password) throws SQLException;
	
	public ArrayList<Users> GetAllUsers() throws SQLException ;
	
	public boolean RegistrationValidation(String email,Long phonenumber) throws SQLException;
	
}
