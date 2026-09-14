package Repository.DAOs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import DTOs.CartItemDTO;
import DTOs.CartProductDTO;
import models.Cart;

public interface CartDAO {
    
	public Optional<Cart> GetCart(int userid) throws SQLException;
	
	public Optional<Cart>  Save(Connection con,int cart_id,int user_id) throws SQLException;
	public void updatequantity(Connection con,int cartid,int productid,int quantity) throws SQLException;
	
	public void DeleteByUserId(Connection con,int userid) throws SQLException;


	public Optional<List<CartItemDTO>> GetCartItems(int userid) throws SQLException;
	
	public void DeleteProductFromCart(Connection con,int cartid,int productid) throws SQLException;
	
	public Optional<List<CartProductDTO>> GetCartProduct(int userid) throws SQLException;
	public void DeleteCart(Connection con,int cartid) throws SQLException;
	
	//public Optional<List<AddToCartDto>> Addtocart(int userid) throws SQLException;
	public void DeleteByCartIdByProductId(Connection con,int cartid, int productid) throws SQLException;
	public void save(Connection con,int cart_item_id,int userid, int quantity, int product_id) throws SQLException;
	
}
