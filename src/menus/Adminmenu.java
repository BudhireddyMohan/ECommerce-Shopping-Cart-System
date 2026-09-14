package menus;

import java.util.Scanner;

import exceptions.GlobelExceptionHandler;
import services.CartServices;
import services.OrderServices;
import services.ProductsServices;
import services.UserServices;

public class Adminmenu {

    //------------------ objects

    GlobelExceptionHandler g = new GlobelExceptionHandler();

    ProductsServices productsservices;
    UserServices userservice;
    OrderServices orderservices;

    public Adminmenu(
            ProductsServices productservices,
            CartServices cartservices,
            OrderServices orderservices,
            UserServices userservices) {

        this.productsservices = productservices;
        this.userservice = userservices;
        this.orderservices = orderservices;
    }

    public void welocomeadminmenu() {

        Scanner sc = new Scanner(System.in);

        boolean flag = true;

        while (flag) {

            try {

                displaymenu();

                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {

                    case 1: {
                        productsservices.addproduct();
                        break;
                    }

                    case 2: {
                        productsservices.updateProduct();
                        break;
                    }

                    case 3: {
                        productsservices.deleteproduct();
                        break;
                    }

                    case 4: {
                        productsservices.ViewAllProducts();
                        break;
                    }

                    case 5: {
                        productsservices.SearchProduct();
                        break;
                    }

                    case 6: {
                        userservice.ViewUsers();
                        break;
                    }

                    case 7: {
                        orderservices.ViewAllOrders();
                        break;
                    }

                    case 8: {
                        orderservices.ViewOrderdetails();
                        break;
                    }

                    case 9: {
                        orderservices.DeveleryAllOrdersPendingOrders();
                        break;
                    }

                    case 10: {
                        orderservices.viewAllOrdersByStatus();
                        break;
                    }

                    case 11: {
                        flag = false;
                        break;
                    }

                    default: {
                        System.out.println("enter correct option");
                    }
                }

            } catch (Exception e) {

                g.handler(e);

            }
        }

        sc.close();
    }

    
    
    
    
    public void displaymenu() {

        System.out.println();

        System.out.println(
                "================================\r\n"
                + "        Welcome Admin\r\n"
                + "================================\r\n"
                + "\r\n"
                + "1. Add Product\r\n"
                + "2. Update Product\r\n"
                + "3. Delete Product\r\n"
                + "4. View All Products\r\n"
                + "5. Search Product\r\n"
                + "6. View All Users\r\n"
                + "7. View All Orders\r\n"
                + "8. View Order Details\r\n"
                + "9. Develery All Pending Orders\r\n"
                + "10. view All Pending Orders\r\n"
                + "11. Logout\r\n"
                + "Enter Choice:"
        );
    }
}