package Repository.DAOimplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import Repository.DAOs.ProductsDAO;
import Utlity.DatabaseTest;
import models.Products;





public class ProductsDAOimp implements ProductsDAO{

	
	DatabaseTest database;
	public ProductsDAOimp(DatabaseTest database){
		this.database=database;
	}
	
	
	@Override
	public void Saveproduct(Products p) throws SQLException{
		
		Connection con=database.getconnection();
		String query="insert into  Products values(?,?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, p.getProductid());
		ps.setString(2, p.getProductname());
		ps.setString(3, p.getCategory());
		ps.setInt(4, p.getPrice());
		ps.setInt(5, p.getStock());
		
		ps.executeUpdate();
		
		
	}


	@Override
	public ArrayList<Products> GetAllProducts() throws SQLException {
        Connection con=database.getconnection();
        Statement s= con.createStatement();
        ResultSet rs=s.executeQuery("select * from Products");
        ArrayList<Products> products=new ArrayList<>();
        while(rs.next()) {
        	Products p=new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5));
        	products.add(p);
        }
        
		return products;
	}


	@Override
	public void updateproduct(Products p) throws SQLException{
		Connection c=database.getconnection();
		String query="update products set  productname=?, category=?, price=?,stock=? where product_id=?";
		PreparedStatement ps=c.prepareStatement(query);
		ps.setString(1, p.getProductname());
		ps.setString(2, p.getCategory());
		ps.setInt(3, p.getPrice());
		ps.setInt(4, p.getStock());
		ps.setInt(5, p.getProductid());
		
	      ps.executeUpdate();
	}


	@Override
	public void DeleteProduct(int deleteproductid) throws SQLException {
		Connection c=database.getconnection();
		String Query="delete from products where product_id=?;";
		PreparedStatement p=c.prepareStatement(Query);
		p.setInt(1, deleteproductid);
		int count = p.executeUpdate();
System.out.println(count);
		System.out.println(
		    count > 0
		        ? "Product deleted from DB"
		        : "Product NOT deleted from DB"
		);
		
	}
	
	


	@Override
	public void  updatestockInProducts(Connection con,int productid, int orderquantity) throws SQLException {
		//Connection con=database.getconnection();
		String query="update products set stock=? where product_id= ?;";
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1,orderquantity );
		ps.setInt(2, productid);
		int rows=ps.executeUpdate();
		 if (rows == 0) {
			 System.out.println("problem in updating stock");
		        return ;
		    }
		 
			String query2="select * from products where product_id=?";
			PreparedStatement ps2=con.prepareStatement(query2);
			ps2.setInt(1, productid);
			ResultSet rs=ps2.executeQuery();
			rs.next();
		    if (rs.next()) {
		         if(rs.getInt(5)==0) {
		        	 DeleteProduct(rs.getInt(5));
		         }
		    }
		    
		}


	@Override
	public Optional<Products> GetProduct(int productid) throws SQLException{
		
		Connection con=database.getconnection();
		String query2="select * from products where product_id=?";
		PreparedStatement ps2=con.prepareStatement(query2);
		ps2.setInt(1, productid);
		ResultSet rs=ps2.executeQuery();
		
		Optional<Products> pro=Optional.empty();
	   if(rs.next()) {
		   Products p=new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)); 
		   return Optional.of(p); 
	   }
		   return pro;	
	    
	}


	@Override
	public Optional<ArrayList<Products>> ProductExitsOrNotSearchByName(String name) throws SQLException {
		Connection con=database.getconnection();
		String query="select * from products where productname=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, name);
		ResultSet rs= ps.executeQuery();
		
		Optional<ArrayList<Products>> pro=Optional.empty();
		ArrayList<Products> addproducts=new ArrayList<>();
		   if(rs.next()) {
			   Products p=new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)); 
			   addproducts.add(p);
		   }
		   if(addproducts.size()==0) {
			   return pro;
		   }
		   
		   pro=Optional.of(addproducts);
		   return pro;
		
	}


	@Override
	public Optional<ArrayList<Products>> GetByCategory(String catrgory) throws SQLException {
		
		Connection con=database.getconnection();
		String query="select * from products where category=?";
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, catrgory);
		ResultSet rs=ps.executeQuery();
		Optional<ArrayList<Products>> productsarray=Optional.empty();
		ArrayList<Products> pro=new ArrayList<>();
		while(rs.next()) {
			pro.add(new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
		}
		if(pro.size()==0) return productsarray;
		 productsarray= Optional.of(pro);
		// System.out.println("productsarray : "+productsarray);
		 return productsarray;
		
	}



		
		
		
		
		
	

}











