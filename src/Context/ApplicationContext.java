package Context;
import Repository.DAOimplement.CartDAOimp;
import Repository.DAOimplement.OrdersDAOimpl;
import Repository.DAOimplement.ProductsDAOimp;
import Repository.DAOimplement.UserDAOimplements;
import Repository.DAOs.CartDAO;
import Repository.DAOs.OrdersDAO;
import Repository.DAOs.ProductsDAO;
import Utlity.DatabaseTest;
import exceptions.GlobelExceptionHandler;
import menus.Adminmenu;
import menus.Usermenu;
import services.CartServices;
import services.OrderServices;
import services.ProductsServices;
import services.UserServices;


public class ApplicationContext {

	CartServices cartservices;
	OrderServices orderservices;
	ProductsServices productservices;
	UserServices userservices; 

	Usermenu usermenu;
	Adminmenu adminmenu;
	GlobelExceptionHandler globalhandler;
	UserDAOimplements userDaoimp;
	DatabaseTest database;
	ProductsDAO productsDAo;
	CartDAO cartDao;
//	Cart_itemsDAos cart_itemdao;
	OrdersDAO ordersdao;
	//Order_itemsDAO order_itemsdao;
	
	public ApplicationContext(){
		
		
		this.globalhandler=new GlobelExceptionHandler();
	
		this.database=new DatabaseTest();
		this.productsDAo=new ProductsDAOimp(database);
		this.productservices=new ProductsServices(globalhandler,productsDAo);
		this.cartDao=new CartDAOimp(database);
		this.ordersdao=new OrdersDAOimpl(database);
		//this.order_itemsdao=new Order_Items_DAOimp(database);
		//this.cart_itemdao=new Cart_itemsDAosimp(database);
		this.cartservices=new  CartServices(productservices,productsDAo,cartDao,database);
		this.orderservices=new OrderServices(productservices,cartservices,globalhandler,ordersdao,database);
		this.userDaoimp=new UserDAOimplements(database);
		this.userservices =new UserServices(globalhandler,userDaoimp);
		this.usermenu=new Usermenu(productservices,cartservices,orderservices,userservices);
	    this.adminmenu=new Adminmenu(productservices,cartservices,orderservices,userservices);
	}

//	public CartServices getCartservices() {
//		return cartservices;
//	}
//
//	public void setCartservices(CartServices cartservices) {
//		this.cartservices = cartservices;
//	}
//
//	public OrderServices getOrderservices() {
//		return orderservices;
//	}
//
//	public void setOrderservices(OrderServices orderservices) {
//		this.orderservices = orderservices;
//	}
//
//	public ProductsServices getProductservices() {
//		return productservices;
//	}
//
//	public void setProductservices(ProductsServices productservices) {
//		this.productservices = productservices;
//	}
//
	public UserServices getUserservices() {
		return userservices;
	}
//
//	public void setUserservices(UserServices userservices) {
//		this.userservices = userservices;
//	}
//
//	public FiledataHandling getFilehandling() {
//		return filehandling;
//	}
//
//	public void setFilehandling(FiledataHandling filehandling) {
//		this.filehandling = filehandling;
//	}
//
	public Usermenu getUsermenu() {
		return usermenu;
	}
//
//	public void setUsermenu(Usermenu usermenu) {
//		this.usermenu = usermenu;
//	}
//
	public Adminmenu getAdminmenu() {
		return adminmenu;
	}
//
//	public void setAdminmenu(Adminmenu adminmenu) {
//		this.adminmenu = adminmenu;
//	}
	
	
}



	