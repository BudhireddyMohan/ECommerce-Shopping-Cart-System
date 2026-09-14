package Repository.DAOs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import models.Products;

public interface ProductsDAO {

	public void Saveproduct(Products product) throws SQLException;
	
	public ArrayList<Products> GetAllProducts() throws SQLException;
	
	public void updateproduct(Products p) throws SQLException;
	
	public void DeleteProduct(int deleteproductid) throws SQLException;
	
	public void updatestockInProducts(Connection con,int productid,int orderquantity)throws SQLException;
	
	public Optional<Products> GetProduct(int productid) throws SQLException;
	
	public Optional<ArrayList<Products>> ProductExitsOrNotSearchByName(String name) throws SQLException;
	
	public Optional<ArrayList<Products>> GetByCategory(String catrgory) throws SQLException;
	
}
