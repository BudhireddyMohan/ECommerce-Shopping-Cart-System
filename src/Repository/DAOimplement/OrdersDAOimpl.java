package Repository.DAOimplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import DTOs.AddToCartDto;
import DTOs.OrderItemsDTOs;
import Repository.DAOs.OrdersDAO;
import Utlity.DatabaseTest;
import models.OrderStatus;

public class OrdersDAOimpl implements OrdersDAO{
	
	DatabaseTest database;

	public OrdersDAOimpl(DatabaseTest database) {
		this.database=database;
	}
	
	
	

	@Override
	public void save(Connection con,int orderid, int userid, LocalDateTime OrderDate, OrderStatus status) throws SQLException {
		//Connection con=database.getconnection();
		
		String query="insert into `order` values(?,?,?,?);";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, orderid);
		ps.setInt(2, userid);
		ps.setObject(3,OrderDate );
		ps.setObject(4, status.name());
		if(1==ps.executeUpdate()) {
			System.out.println("data saved to orders table in DB");
		}
		else {
			System.out.println("data not saved to orders table in DB");
		}
		
		
	}

	
	


	@Override
	public Optional<List<OrderItemsDTOs>> GetAllOrders() throws SQLException {
		
		Connection con=database.getconnection();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("select o.order_id,o.status,oi.product_name,oi.price,oi.quantity   from `order` o inner join order_items oi on o.order_id=oi.order_id;");
		List<OrderItemsDTOs> orders=new ArrayList<>();
		while(rs.next()) {
			orders.add(new OrderItemsDTOs(rs.getInt(1),OrderStatus.valueOf(rs.getString(2)),rs.getString(3),rs.getInt(4),rs.getInt(5)));
		}
		
		
		 if(orders.size()==0) return Optional.empty();
		 return Optional.of(orders);
	}
	


	@Override
	public Optional<List<OrderItemsDTOs>> GetOrdersByStatus(OrderStatus status) throws SQLException {
		
		
		String Query="select o.order_id,o.status,oi.product_name,oi.price,oi.quantity   from `order` o inner join order_items oi on o.order_id=oi.order_id where o.status=?; ";
		Connection con=database.getconnection();
		
		PreparedStatement ps=con.prepareStatement(Query);
		ps.setObject(1, status.name());
		List<OrderItemsDTOs> a = new ArrayList<>();
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			a.add(new OrderItemsDTOs(rs.getInt(1),OrderStatus.valueOf(rs.getString(2)),rs.getString(3),rs.getInt(4),rs.getInt(5)));
		}
		if(a.size()==0) return Optional.empty();
		
		return Optional.of(a);
		
	}

	@Override
	public void UpdateOrderStatus(int orderid) throws SQLException {
		String Query="update `order` set status='DELIVERED' where order_id=?;";
		Connection con=database.getconnection();
		
		PreparedStatement ps=con.prepareStatement(Query);
		ps.setObject(1, orderid);
		
		if(1==ps.executeUpdate()) {
			System.out.println("Order status Changed to delivered in Db");
		}
		else {
			System.out.println("Order status Not Changed to delivered in Db");
		}
		

	}

	@Override
	public Optional<List<OrderItemsDTOs>> GetOrderItemsByOrderId(int userid) throws SQLException {
		
		
		String query=" select o.order_id,o.status,oi.product_name,oi.price,oi.quantity from `order` o"
				+ " inner join order_items oi on o.order_id=oi.order_id inner join users u on u.userid=o.user_id"
				+ " where u.userid=? ;";
		
		Connection con=database.getconnection();
		
		PreparedStatement ps=con.prepareStatement(query);
		
		ps.setInt(1, userid);
		
		ResultSet rs=ps.executeQuery();
		
		List<OrderItemsDTOs> orderitems=new ArrayList<>();
		
		while(rs.next()) {
			orderitems.add(new OrderItemsDTOs(rs.getInt(1),OrderStatus.valueOf(rs.getString(2)),rs.getString(3),rs.getInt(4),rs.getInt(5)));
		}
		
		if(orderitems.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(orderitems) ;
	}




	@Override
	public Optional<List<AddToCartDto>> Addtocart(Connection con,int userid) throws SQLException {
		//Connection con=database.getconnection();
		String query="select c.cart_id,ci.quantity,ci.product_id,p.product_id,p.productname,p.price,p.stock from cart c inner join cart_items ci on c.cart_id=ci.cart_id inner join products p on p.product_id=ci.product_id where user_id=? ;";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, userid);
		ResultSet rs=ps.executeQuery();
		
		List<AddToCartDto> a=new ArrayList<>();
		while(rs.next()) {
			a.add(new AddToCartDto(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getInt(7)));
		}
		
		return a.isEmpty() ? Optional.empty() : Optional.of(a);
	
	}




	@Override
	public void save(Connection con,int order_items_id, int orderid, String productname, int price, int quantity) throws SQLException {
		//Connection con=database.getconnection();
		String query=" insert into order_items values(?,?,?,?,?);";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, order_items_id);
		ps.setInt(2, orderid);
		ps.setString(3, productname);
		ps.setInt(4, price);
		ps.setInt(5, quantity);
		if(1==ps.executeUpdate()) {
			System.out.println("Data is inserted into order_items DB");
		}
		else {
			System.out.println("Data i not inserted into order_items DB");
		}
		
	}




	@Override
	public void locking_Products_Row(Connection con, int productid) throws SQLException {
		String query= "select * from products where product_id=? for update ;";
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, productid);
		
		 System.out.println(ps.execute() ? " rows Locked sucessfullly":"row locked fail"); 
		
	}




	@Override
	public void DelectByOrderid(Connection con,int orderid) throws SQLException {
	   String Query=" delete from `order` where order_id=?; ";
	   
	   PreparedStatement ps=con.prepareStatement(Query);
	   ps.setInt(1, orderid);
	  if( ps.executeUpdate()>=1) {
		  System.out.println("deleted the order and orderitems in the DB By Cascding");
	  }else {
		  System.out.println("error at the deleting the order and orderitems using the cascading");
	  }
	   
	   
		
	}




	@Override
	public int SalesOfDay() throws SQLException {
		
		Connection con=database.getconnection();
	   Statement st=	con.createStatement();
		String Query="select sum(oi.price)  from `order` o left join order_items oi on o.order_id=oi.order_id   where   date(orderdate)  =current_date()   ;"; 
		ResultSet rs= st.executeQuery(Query);
		int total=0;
	while(rs.next()) {
		total=rs.getInt(1);
	}
		
		return total;
	}
	
	
	
	
	

}
