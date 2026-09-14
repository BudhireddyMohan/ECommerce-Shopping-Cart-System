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
	
	//public Optional<List<Order>> GetOrderByUserID(int userid) throws SQLException;
	
	//public Optional<List<Order>> GetOrderByOrderId(int orderid) throws SQLException;
	
	//public  Optional<List<Order>> GetAllOrders()throws SQLException;
	
	public Optional<List<OrderItemsDTOs>> GetOrdersByStatus(OrderStatus status) throws SQLException;
	
	public void UpdateOrderStatus(int orderid) throws SQLException;
	
	public Optional<List<OrderItemsDTOs>> GetOrderItemsByOrderId(int userid) throws SQLException;
	
	public Optional<List<OrderItemsDTOs>> GetAllOrders() throws SQLException;
	
	public Optional<List<AddToCartDto>> Addtocart(int userid) throws SQLException;
	public void save(Connection con,int order_items_id,int orderid,String productname,int price , int quantity) throws SQLException;
	
}
