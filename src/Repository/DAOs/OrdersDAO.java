package Repository.DAOs;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import DTOs.AddToCartDto;
import DTOs.OrderItemsDTOs;
import models.OrderStatus;

public interface OrdersDAO {

	public void save(Connection con,int orderid,int userid, LocalDateTime OrderDatetime,OrderStatus status) throws SQLException;
	
	
	
	public Optional<List<OrderItemsDTOs>> GetOrdersByStatus(OrderStatus status) throws SQLException;
	
	public void UpdateOrderStatus(int orderid) throws SQLException;
	
	public Optional<List<OrderItemsDTOs>> GetOrderItemsByOrderId(int userid) throws SQLException;
	
	public Optional<List<OrderItemsDTOs>> GetAllOrders() throws SQLException;
	
	public Optional<List<AddToCartDto>> Addtocart(Connection con,int userid) throws SQLException;
	public void save(Connection con,int order_items_id,int orderid,String productname,int price , int quantity) throws SQLException;
	
	
	public void locking_Products_Row(Connection con,int productid) throws SQLException;
	
	
	public void DelectByOrderid(Connection con,int orderid) throws SQLException;
	
	public int SalesOfDay() throws SQLException;
	
}
