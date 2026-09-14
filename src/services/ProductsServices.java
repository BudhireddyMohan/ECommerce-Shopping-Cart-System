package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import Repository.DAOs.ProductsDAO;
import exceptions.GlobelExceptionHandler;
import exceptions.InCorrectProduct;
import exceptions.ProductAlreadyExits;
import models.Products;

public class ProductsServices {
	
	
	
	////------------objects 

	ProductsDAO productsDAo;
	GlobelExceptionHandler globelhandler;
	
		
	public ProductsServices(GlobelExceptionHandler globelhandler,ProductsDAO productsDAo) {
	
		this.globelhandler=globelhandler;
		this.productsDAo=productsDAo;
	}
	
	
	Scanner sc =new Scanner(System.in);
	
	
	//-------------------add product
	public void addproduct() throws SQLException,ProductAlreadyExits {
		
		System.out.println("adding product");
		System.out.println("==================");
		System.out.println("enter Productname");
		String productname=sc.nextLine();
		
		while(productname.equals("")) {
			System.out.println("Productname should not be Empty");
			productname=sc.nextLine();
		}
		
		
		if(productsDAo.ProductExitsOrNotSearchByName(productname).isPresent()) 
			 new ProductAlreadyExits("Product Already Exits");
		
		
		System.out.println("enter category");
		String category=sc.nextLine();
		while(category.equals("")) {
			System.out.println("should not be empty");
			category=sc.nextLine();
		}
		
		System.out.println("enter the price");
		int price=sc.nextInt() ;
		sc.nextLine();
		while(price<=0) {
			System.out.println("Price must be greater than 0");
			 price=sc.nextInt() ;
			 sc.nextLine();
		}
		
		System.out.println("enter the stock");
		int stock=sc.nextInt() ;
		sc.nextLine();
		while(stock<=0) {
			System.out.println("stock must be greater than 0");
			stock=sc.nextInt() ;
			sc.nextLine();
		}
		
		
		productsDAo.Saveproduct(new Products(UserServices.generateid(),  productname,  category,  price, stock));
		
		System.out.println("==============================\r\n"
				+ "Product Added Successfully To DB!\r\n"
				+ "==============================");
	}
	
	
//-------------------------update Products
	public void updateProduct() throws InCorrectProduct,SQLException{
		System.out.println("==============================\r\n"
				+ "        Update Product\r\n"
				+ "==============================\r\n");
		
		System.out.println("enter the product id u wanted to update");
		int productid=  Integer.parseInt(sc.nextLine());
		
		Products updateproduct=GetProduct(productid);
		System.out.println("Product details : ");
		System.out.println(updateproduct.toString());
		System.out.println("==============================\r\n"
				+ updateproduct.getProductname()+"\r\n"
				+ "==============================\r\n"
				+ "1. Update Name\r\n"
				+ "2. Update Category\r\n"
				+ "3. Update Price\r\n"
				+ "4. Update Stock\r\n"
				+ "5. Back\r\n"
				+ "\r\n"
				+ "Enter Choice:");
		int option =sc.nextInt();
		sc.nextLine();
		switch(option) {
		case 1:{
			System.out.println("enter name: ");
			String name=sc.nextLine();
			while(name.equals("")) {
				System.out.println("Productname should not be Empty");
				name=sc.nextLine();
			}
			updateproduct.setProductname(name);
			break;
		}
		case 2:{
			System.out.println("enter Update Category :");
			String name=sc.nextLine();
			while(name.equals("")) {
				System.out.println("Productname should not be Empty");
				name=sc.nextLine();
			}
			updateproduct.setCategory(name);
			break;
		}
		case 3:{
			System.out.println("Update Price : ");
			int num=sc.nextInt();
			while(num<=0) {
				System.out.println("stock must be greater than 0");
				num=sc.nextInt() ;
				sc.nextLine();
			}
			updateproduct.setPrice(num);
			break;
		}
		case 4:{
			System.out.println("enter Update Stock");
			int num=sc.nextInt();
			while(num<=0) {
				System.out.println("stock must be greater than 0");
				num=sc.nextInt() ;
				sc.nextLine();
			}
			updateproduct.setStock(num); 
			break;
		}
		case 5:{
			return;
		
		}
		default :{
			System.out.println("enter correct option");
		}
		}
		
		productsDAo.updateproduct(updateproduct);
		
		System.out.println("==============================\r\n"
				+ "Product Updated  Successfully!\r\n"
				+ "==============================");
	}
	
	
	
//------------------------------------------------deleteproduct
	public void deleteproduct() throws SQLException,InCorrectProduct{
		System.out.println("==============================\r\n"
				+ "        Delete Product\r\n"
				+ "==============================");
		
		System.out.println("enter the product id to delete");
		int id=sc.nextInt();
		sc.nextLine();
		GetProduct(id);
		System.out.println("Are you sure you want to delete?  1- Yes 2- No");
		int yesno=sc.nextInt();
		sc.nextLine();
		switch(yesno){
		case 1:{
			productsDAo.DeleteProduct(id);
			System.out.println("==============================\r\n"
					+ "        Delete Product Sucessfully\r\n"
					+ "==============================");
		}case 2:{
			return ;
		}
		}
	}
	
	
	
	//-----------------------------updatestock
	public void updatestock(Connection con,int productid,int orderquantity) throws SQLException {
		productsDAo.updatestockInProducts(con,productid,orderquantity);	
	}
	
	
	
	
//---------------------------------ViewAllProducts
	
	
	
	public void ViewAllProducts() throws SQLException{
	System.out.println("========================================\r\n"
			+ "             ALL PRODUCTS\r\n"
			+ "========================================");	
	System.out.printf("%-5s %-18s %-14s %-10s %s%n", "ID", "Product Name", "Category", "Price", "Stock");
	System.out.println("---------------------------------------------------------");
	ArrayList<Products> products=productsDAo.GetAllProducts();
	for (Products ele : products) {
	    System.out.printf("%-5s %-18s %-14s %-10s %s%n", 
	        String.valueOf(ele.getProductid()), 
	        String.valueOf(ele.getProductname()), 
	        String.valueOf(ele.getCategory()), 
	        String.valueOf(ele.getPrice()), 
	        String.valueOf(ele.getStock())
	    );
	}
	}
	
	
	
//---------------------SearchProduct
	
	
	
	
	
	public void SearchProduct() throws SQLException,InCorrectProduct{
		System.out.println("==============================\r\n"
				+ "       Search Product\r\n"
				+ "==============================\r\n"
				+ "\r\n"
				+ "1. Search by Product ID\r\n"
				+ "2. Search by Product Name\r\n"
				+ "3. Search by Category\r\n"
				+ "4. Back\r\n"
				+ "\r\n"
				+ "Enter Choice:");
		
		int option=sc.nextInt();
		sc.nextLine();
		switch(option) {
		case 1:{
			System.out.println("enter product Id");
			int productid=sc.nextInt();
			sc.nextLine();
			System.out.println( GetProduct(productid).toString());
			break;
		}
		case 2:{
			System.out.println("enter product name");
			String name=sc.nextLine().trim().toLowerCase();
			System.out.printf("%-5s %-18s %-14s %-10s %s%n", "ID", "Product Name", "Category", "Price", "Stock");
			System.out.println("---------------------------------------------------------");
			Optional<ArrayList<Products>> products=productsDAo.ProductExitsOrNotSearchByName(name);
			  ArrayList< Products> p=products.orElseThrow(()-> new InCorrectProduct("Products Not founded By the Name ")  );
			for (Products ele : p) {
			    if (ele != null && ele.getProductname() != null) {
			        if (ele.getProductname().toLowerCase().contains(name)) {
			            System.out.printf("%-5s %-18s %-14s %-10s %s%n", 
			                String.valueOf(ele.getProductid()), 
			                String.valueOf(ele.getProductname()), 
			                String.valueOf(ele.getCategory()), 
			                String.valueOf(ele.getPrice()), 
			                String.valueOf(ele.getStock())
			            );
			        }
			    }
			}
			break;
		}
		case 3:{
			System.out.println("Search by Category");
			String name=sc.nextLine().trim().toLowerCase();
			
			System.out.printf("%-5s %-18s %-14s %-10s %s%n", "ID", "Product Name", "Category", "Price", "Stock");
			System.out.println("---------------------------------------------------------");
			
			Optional<ArrayList<Products>> products=productsDAo.GetByCategory(name);
			ArrayList<Products> p=products.orElseThrow(()->  new InCorrectProduct("In correct Product category") );
			System.out.println("p :  "+p);
			for (Products ele : p) {			 
			         System.out.printf("%-5s %-18s %-14s %-10s %s%n", 
			                String.valueOf(ele.getProductid()), 
			                String.valueOf(ele.getProductname()), 
			                String.valueOf(ele.getCategory()), 
			                String.valueOf(ele.getPrice()), 
			                String.valueOf(ele.getStock())
			            );    
			}
			break;
		}
		case 4:{
			return;
		}
		}
		
	}
	
	
	
	
	
	
//-----------------------------GetProductStock
	public int GetProductStock(int proid) throws SQLException {
		Optional<Products> product= productsDAo.GetProduct(proid);
		//return product.get().getStock();
		return product.get().getStock();
	}
	
	
	
	
	
//-------------------------DoesProductsContaInProductid
	public Products GetProduct(int productid) throws SQLException,InCorrectProduct {
		
		Optional<Products> product= productsDAo.GetProduct(productid);
		 return  product.orElseThrow(()->new InCorrectProduct("entered In Correct Product Id")); 
		
	}
	

	
//--------------------------------getproductprice
	public int  getproductprice(int proid) throws SQLException,ProductAlreadyExits {	
		Optional<Products> product= productsDAo.GetProduct(proid);
		Products pro=product.orElseThrow(()-> new InCorrectProduct("Product Not Exists to get price"));
	     return pro.getPrice();
   }
	
}
