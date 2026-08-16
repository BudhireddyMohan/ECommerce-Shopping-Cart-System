package Context;
import exceptions.GlobelExceptionHandler;
import fileHandlind.FiledataHandling;
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
	FiledataHandling filehandling;
	Usermenu usermenu;
	Adminmenu adminmenu;
	GlobelExceptionHandler globalhandler;
	
	public ApplicationContext(){
		this.globalhandler=new GlobelExceptionHandler();
		this.filehandling=new FiledataHandling();
		this.productservices=new ProductsServices(filehandling);
		this.cartservices=new  CartServices(filehandling,productservices);
		this.orderservices=new OrderServices(filehandling,productservices,cartservices,globalhandler);
		this.userservices =new UserServices(filehandling);
		this.usermenu=new Usermenu(filehandling,productservices,cartservices,orderservices,userservices);
	  this.adminmenu=new Adminmenu(productservices,cartservices,orderservices,userservices);
	}

	public CartServices getCartservices() {
		return cartservices;
	}

	public void setCartservices(CartServices cartservices) {
		this.cartservices = cartservices;
	}

	public OrderServices getOrderservices() {
		return orderservices;
	}

	public void setOrderservices(OrderServices orderservices) {
		this.orderservices = orderservices;
	}

	public ProductsServices getProductservices() {
		return productservices;
	}

	public void setProductservices(ProductsServices productservices) {
		this.productservices = productservices;
	}

	public UserServices getUserservices() {
		return userservices;
	}

	public void setUserservices(UserServices userservices) {
		this.userservices = userservices;
	}

	public FiledataHandling getFilehandling() {
		return filehandling;
	}

	public void setFilehandling(FiledataHandling filehandling) {
		this.filehandling = filehandling;
	}

	public Usermenu getUsermenu() {
		return usermenu;
	}

	public void setUsermenu(Usermenu usermenu) {
		this.usermenu = usermenu;
	}

	public Adminmenu getAdminmenu() {
		return adminmenu;
	}

	public void setAdminmenu(Adminmenu adminmenu) {
		this.adminmenu = adminmenu;
	}
	
	
}



	