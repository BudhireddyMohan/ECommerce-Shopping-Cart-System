package Context;
import Async_Programming_Layer.ExecutorServices;
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
import services.EmailService;
import services.OrderServices;
import services.ProductsServices;
import services.UserServices;


public class ApplicationContext {
	
	
	

	public static ApplicationContext instance;
	
	
	
	CartServices cartservices;
	OrderServices orderservices;
	ProductsServices productservices;
	UserServices userservices; 
	ExecutorServices serviceexecutor;
	Usermenu usermenu;
	Adminmenu adminmenu;
	GlobelExceptionHandler globalhandler;
	UserDAOimplements userDaoimp;
	DatabaseTest database;
	ProductsDAO productsDAo;
	CartDAO cartDao;
	EmailService emailservice;
	OrdersDAO ordersdao;
	
	
	
	private ApplicationContext(){
		
		
		this.globalhandler=new GlobelExceptionHandler();
	    this.serviceexecutor=new ExecutorServices();
	    this.emailservice=new EmailService();
		this.database=new DatabaseTest();
		this.productsDAo=new ProductsDAOimp(database);
		this.productservices=new ProductsServices(globalhandler,productsDAo);
		this.cartDao=new CartDAOimp(database);
		this.ordersdao=new OrdersDAOimpl(database);
     //    this.adminservices=new AdimServices(database);
		this.cartservices=new  CartServices(productservices,productsDAo,cartDao,database);
		this.orderservices=new OrderServices(productservices,cartservices,globalhandler,ordersdao,database,serviceexecutor,emailservice);
		this.userDaoimp=new UserDAOimplements(database);
		this.userservices =new UserServices(globalhandler,userDaoimp);
		this.usermenu=new Usermenu(productservices,cartservices,orderservices,userservices);
	    this.adminmenu=new Adminmenu(productservices,cartservices,orderservices,userservices);
	}
	
	
	
	
	
	public static  ApplicationContext getinstance() {
		if(instance==null) {
			instance=new ApplicationContext();
		}
		
		return instance;
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



	