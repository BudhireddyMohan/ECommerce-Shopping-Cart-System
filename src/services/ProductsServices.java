package services;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.GlobelExceptionHandler;
import exceptions.InCorrectProduct;
import exceptions.ProductAlreadyExits;
import fileHandlind.FiledataHandling;
import models.Products;

public class ProductsServices {
	
	ArrayList<Products> products=new ArrayList<>();
	FiledataHandling filehandling;
	
	
	
		
	public ProductsServices(FiledataHandling filehandling2) {
		this.filehandling=filehandling2;
		this.products=filehandling.loaddata("Products.dta", products);

	}


	//------------objects 
	
	public ProductsServices() {
		// TODO Auto-generated constructor stub
	}


	GlobelExceptionHandler globelhandler=new GlobelExceptionHandler();
	
	
	
	
	Scanner sc =new Scanner(System.in);
	
	
	//-------------------add product
	public void addproduct() {
		
		System.out.println("adding product");
		System.out.println("==================");
		System.out.println("enter Productname");
		String productname=sc.nextLine();
		
		while(productname.equals("")) {
			System.out.println("Productname should not be Empty");
			productname=sc.nextLine();
		}
		if(ProductExitsOrNot(productname)) {
			throw new ProductAlreadyExits("Product Already Exits");
		}
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
		
		Products p=new Products(generateid(),  productname,  category,  price, stock);
		products.add(p);
		filehandling.savedata("Products.dta", products);
		System.out.println("==============================\r\n"
				+ "Product Added Successfully!\r\n"
				+ "==============================");
		
		
	}
	
	
//-------------------------update Products
	public void updateProduct() {
		System.out.println("==============================\r\n"
				+ "        Update Product\r\n"
				+ "==============================\r\n");
		
		System.out.println("enter the product id u wanted to update");
		int productid=sc.nextInt();
		sc.nextLine();
		Products updateproduct=DoesProductsContaInProductid(productid);
		
		if(updateproduct==null) {
			throw new InCorrectProduct("entered In Correct Product Id");
			
		}
		int index=products.indexOf(updateproduct);
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
			updateproduct.setStock(num); ;
			break;
		}
		case 5:{
			return;
		
		}
		default :{
			System.out.println("enter correct option");
		}
		}
		//set in arraylist
		products.set(index, updateproduct);
		System.out.println("updated product"+products.get(index).toString());
		filehandling.savedata("Products.dta", products);
		System.out.println("==============================\r\n"
				+ "Product Updated  Successfully!\r\n"
				+ "==============================");
	}
	
	
	
//------------------------------------------------deleteproduct
	public void deleteproduct() {
		System.out.println("==============================\r\n"
				+ "        Delete Product\r\n"
				+ "==============================");
		
		System.out.println("enter the product id to delete");
		int id=sc.nextInt();
		sc.nextLine();
		Products deleteproduct=  DoesProductsContaInProductid(id);
		if(deleteproduct==null) {
			throw new InCorrectProduct("entered In Correct Product Id");
			
		}
		System.out.println("Are you sure you want to delete?  1- Yes 2- No");
		int yesno=sc.nextInt();
		sc.nextLine();
		switch(yesno){
		case 1:{
			products.remove(products.indexOf(deleteproduct));
			filehandling.savedata("Products.dta", products);
			System.out.println("==============================\r\n"
					+ "        Delete Product Sucessfully\r\n"
					+ "==============================");
		}case 2:{
			return ;
		}
		}
	}
	
	
	
	
	
	
	//-----------------------------updatestock
	public void updatestock(int productid,int orderquantity) {
		for(int i=0;i<products.size();i++) {
			
			if(products.get(i).getProductid()==productid) {
				//System.out.println("stock before update : "+ ele.getStock());
				products.get(i).setStock(products.get(i).getStock()-orderquantity);
				if(products.get(i).getStock()==0) {
					products.remove(i);
				}
				filehandling.savedata("Products.dta", products);
				return;
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//---------------------------------ViewAllProducts
	
	
	
	public void ViewAllProducts() {
	System.out.println("========================================\r\n"
			+ "             ALL PRODUCTS\r\n"
			+ "========================================");	
	System.out.printf("%-5s %-18s %-14s %-10s %s%n", "ID", "Product Name", "Category", "Price", "Stock");
	System.out.println("---------------------------------------------------------");
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
	
	public void SearchProduct() {
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
			Products pr=DoesProductsContaInProductid(productid);
			if(pr==null) {
				System.out.println("Product with ID "+productid+ " not found.");
				return ;
			}
			System.out.println(pr.toString());
			break;
		}
		case 2:{
			System.out.println("enter product name");
			String name=sc.nextLine().trim().toLowerCase();
			System.out.printf("%-5s %-18s %-14s %-10s %s%n", "ID", "Product Name", "Category", "Price", "Stock");
			System.out.println("---------------------------------------------------------");
			for (Products ele : products) {
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
			String safeSearchName = (name == null) ? "" : name.trim().toLowerCase();
			System.out.printf("%-5s %-18s %-14s %-10s %s%n", "ID", "Product Name", "Category", "Price", "Stock");
			System.out.println("---------------------------------------------------------");
			for (Products ele : products) {
			    if (ele != null && ele.getProductname() != null) {
			        if (ele.getProductname().toLowerCase().contains(safeSearchName)) {
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
		case 4:{
			return;
		}
		}
		
	}
	
	
	
	
	
	
//-----------------------------GetProductStock
	public int GetProductStock(int proid) {
		for(Products ele: products) {
			if(ele.getProductid()==proid) return ele.getStock();
		}
		return 0;
	}
	
	
	
	
//-------------------------DoesProductsContaInProductid
	public Products DoesProductsContaInProductid(int productid) {
		for(Products ele : products) {
			if(productid==ele.getProductid()) return ele;
		}
		return null;
	}
	
//---------------------generate id
	public int generateid() {
		if(products.size()==0) return 1;
		return products.get(products.size()-1).getProductid()+1;
	}
	
//---------------------------------getproductprice
	public int getproductprice(int proid) {
		for(Products ele: products ) {
			if(ele.getProductid()==proid) return ele.getPrice();	
		}
		return 0;
	}
	
	
	
	
///--------------------	Product Exits Or Not
	public boolean ProductExitsOrNot(String productname) {
		for(Products ele: products) {
			if(ele.getProductname().equals(productname)) {
				return true;
			}
		}
		return false;
	}
//------------------getProductNane
	
	
}
