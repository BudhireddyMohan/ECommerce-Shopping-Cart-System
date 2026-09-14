package Repository.DAOimplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import DTOs.CartItemDTO;
import DTOs.CartProductDTO;
import Repository.DAOs.CartDAO;
import Utlity.DatabaseTest;
import models.Cart;

public class CartDAOimp implements CartDAO {

	
	DatabaseTest database;
	public CartDAOimp(DatabaseTest database) {
		this.database=database;
	}
	
	
	
	@Override
	public Optional<Cart> GetCart(int userid) throws SQLException {
		Connection con=database.getconnection();
		String query="select * from cart where User_id= ?;";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, userid);
		ResultSet rs=ps.executeQuery();
		
		Optional<Cart> cart=Optional.empty();
		while(rs.next()) {
			
	
			Cart c=new Cart(rs.getInt(1),rs.getInt(2));
			cart=Optional.of(c);
		}	
		return cart;
	}



	@Override
	public Optional<Cart> Save(Connection con,int cart_id, int user_id) throws SQLException {
		
		String query="insert into cart value(?,?)";
		PreparedStatement ps=con.prepareStatement(query);
	    ps.setInt(1, cart_id);
	    ps.setInt(2, user_id);
	    if(1==ps.executeUpdate()) {
	    	String query2="select * from cart where user_id=?;";
	    	PreparedStatement ps2=con.prepareStatement(query2);
	    	ps2.setInt(1, user_id);
	    	Optional<Cart> cart=Optional.empty();
	    	ResultSet rs=ps2.executeQuery();
	    	while(rs.next()) {
	    			cart =Optional.of(new Cart(rs.getInt(1),rs.getInt(2))); 
	    	}
	    	return cart;
	    	
	    	
	    }
	    
	    
	   return  GetCart(user_id);
		
	}



	@Override
	public void DeleteByUserId(Connection con,int userid) throws SQLException {
		//Connection con=database.getconnection();
		String query="delete from cart where user_id=?;";
		PreparedStatement ps=con.prepareStatement(query);
		
		ps.setInt(1, userid);
		if(ps.executeUpdate()==1) {
			System.out.println("Deleted the Cart SucessFully");
		}
		
	}



	@Override
	public Optional<List<CartItemDTO>> GetCartItems(int userid) throws SQLException {
		Connection con=database.getconnection();

		String query="SELECT p.productname,p.price,ci.quantity FROM users u INNER JOIN cart c ON u.userid = c.user_id INNER JOIN cart_items ci ON c.cart_id = ci.cart_id INNER JOIN products p ON p.product_id = ci.product_id WHERE u.userid = ?;";
		           
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, userid);
		ResultSet rs=ps.executeQuery();
		
		
		List<CartItemDTO> cartitems=new ArrayList<>();
		while(rs.next()) {
			cartitems.add(new CartItemDTO(rs.getString(1),rs.getInt(2),rs.getInt(3)) );
		}
		
		if(cartitems.isEmpty()) return Optional.empty();
		return Optional.of(cartitems);
	}



	@Override
	public Optional<List<CartProductDTO>> GetCartProduct(int userid) throws SQLException {
		Connection con=database.getconnection();
		String query = "SELECT ci.product_id,p.stock,ci.quantity,c.cart_id FROM users u INNER JOIN cart c ON u.userid = c.user_id INNER JOIN cart_items ci ON c.cart_id = ci.cart_id INNER JOIN products p ON p.product_id = ci.product_id WHERE u.userid = ?;";
	   PreparedStatement ps=con.prepareStatement(query);
	   ps.setInt(1, userid);
	   
	   
	   List<CartProductDTO> a=new ArrayList<>();
	   ResultSet rs=ps.executeQuery();
	   while(rs.next()) {
		  a.add(new CartProductDTO(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4))); 
	   }
	   
	   if(a.isEmpty()) {return Optional.empty();}
	   
	   return Optional.of(a);
		
	}



	@Override
	public void save(Connection con,int cart_item_id, int userid, int quantity, int product_id) throws SQLException {
		//Connection con=database.getconnection();
		String query="insert into cart_items values(?,?,?,?);";
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, cart_item_id);
		ps.setInt(2, userid);
		ps.setInt(3, quantity);
		ps.setInt(4, product_id);
		
		if(1==ps.executeUpdate()) { 
			System.out.println("data inserted sucessfully");
			return;
					}
		System.out.println("error ata inserting data into db");
		
	}

	
	@Override
	public void updatequantity(Connection con,int cartid,int productid, int quantity) throws SQLException {
		
		System.out.println("cartitemsid : "+cartid);
		System.out.println("productid : "+productid);
		System.out.println("quantity : "+quantity);
		
		//Connection con=database.getconnection();
		
		String query="update cart_items set quantity=? where cart_id =? and product_id=?;";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, quantity);
		ps.setInt(2, cartid);
		ps.setInt(3,productid);
		
		
		if(1==ps.executeUpdate()) {
			System.out.println("added to the DB");
			String query1="select * from cart_items where cart_id=? and product_id=? ";
			Connection conn=database.getconnection();
			PreparedStatement ps2=conn.prepareStatement(query1);
			ps2.setInt(1, cartid);
			ps2.setInt(2, productid);
			ResultSet rs=ps2.executeQuery();
			rs.next();
		System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" ");
			
			
			
			
		}
		else {
			System.out.println("Not added to the DB");
		}
		
	}
	
	@Override
	public void DeleteProductFromCart(Connection con,int cartid, int productid) throws SQLException {
		
		String query="delete from  cart_items where cart_id =? and product_id=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, cartid);
		ps.setInt(2, productid);
		
		int rs=ps.executeUpdate();
		if(rs==1) System.out.println("deleted the produt from cart sucessfully");
		else System.out.println("deleted the produt from cart sucessfully");
	}
	
	
	@Override
	public void DeleteCart(Connection con,int cartid) throws SQLException {
	     //Connection con=database.getconnection();
	     String Query="delete from cart where cart_id=?;";
	   PreparedStatement ps=  con.prepareStatement(Query);
	   ps.setInt(1, cartid);
	   if(1==ps.executeUpdate()) {System.out.println("cart deleted besause of no items ");return ;}
	   else System.out.println("error at the delecting cart");
		
	}
	
	
	@Override
	public void DeleteByCartIdByProductId(Connection con,int cartid, int productid) throws SQLException {
		//Connection con=database.getconnection();
		String query="delete from cart_items where cart_id=? and product_id=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1,cartid);
		ps.setInt(2, productid);
		
		if(ps.executeUpdate()==1) {
			System.out.println("Deleted the Row from CartItems");
		}
		else System.out.println("Not Deleted the Row from CartItems");
	}




	

}
