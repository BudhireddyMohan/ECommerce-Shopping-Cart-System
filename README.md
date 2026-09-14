# E-Commerce Shopping Cart System

A **Core Java based console application** that simulates an E-Commerce Shopping Cart System. The project demonstrates real-world business workflows using **Object-Oriented Programming, Collections, Exception Handling, File Handling, Interfaces, and Java Serialization**.

## 📌 Project Overview

The E-Commerce Shopping Cart System allows customers to browse products, search products, add products to their cart, place orders, and view their order history.

Administrators can manage products, view registered users, and view customer orders.

The application uses `.dat` files to permanently store application data.

---

## 🚀 Features

### 👤 User Features

* User Registration
* User Login
* View All Products
* Search Product

  * Search by Product ID
  * Search by Product Name
  * Search by Category
* Add Product to Cart
* View Cart
* Remove Product from Cart
* Place Order
* View Order History
* View My Profile
* Logout

### 👨‍💼 Admin Features

* Admin Login
* Add Product
* Update Product
* Delete Product
* View All Products
* Search Product
* View All Users
* View All Orders
* Logout

---

## 🛒 Place Order Workflow

The application follows a simple shopping workflow:

```text
User Login
    ↓
View Products
    ↓
Add Product to Cart
    ↓
View Cart
    ↓
Place Order
    ↓
Validate Cart
    ↓
Check Product Stock
    ↓
Calculate Order Total
    ↓
Create Order
    ↓
Create Order Items
    ↓
Update Product Stock
    ↓
Clear Cart
    ↓
Save Order
```

---

## 👨‍💼 Admin Workflow

```text
Admin Login
    ↓
Admin Menu
    ↓
Add / Update / Delete Products
    ↓
View Products
    ↓
View Users
    ↓
View Orders
    ↓
Logout
```

---

## 👤 User Workflow

```text
User Registration / Login
          ↓
     User Menu
          ↓
    View Products
          ↓
    Search Product
          ↓
 Add Product to Cart
          ↓
      View Cart
          ↓
     Place Order
          ↓
    Order History
          ↓
        Logout
```

---

## 🧱 Project Structure

```text
ECommerceShopping
│
├── src
│   ├── model
│   ├── service
│   ├── exception
│   ├── utility
│   └── ECommerceShopping.java
│
├── Database
│   ├── users.dat
│   ├── products.dat
│   ├── carts.dat
│   └── orders.dat
│
└── README.md
```

---

## 💾 Data Storage

The application uses Java object serialization to store data permanently in `.dat` files.

```text
users.dat
    ↓
User data

products.dat
    ↓
Product data

carts.dat
    ↓
Cart data

orders.dat
    ↓
Order data
```

The application loads the data when required and updates the corresponding files after operations.

---

## 🧩 Main Entities

### User

Represents a registered customer or application user.

### Product

Represents a product available for purchase.

### Cart

Represents the shopping cart of a user.

### CartItems

Represents a product and its quantity inside a user's cart.

### Order

Represents a completed purchase made by a user.

### OrderItems

Represents individual products purchased as part of an order.

---

## 🔗 Entity Relationship

```text
User
 │
 ├──────────────► Cart
 │                  │
 │                  └── CartItems
 │
 └──────────────► Orders
                    │
                    └── OrderItems
                           │
                           ▼
                        Product
```

---

## 🛠️ Technologies Used

* Java 17
* Core Java
* Object-Oriented Programming
* Collections Framework
* Exception Handling
* Interfaces
* File Handling
* Java Serialization
* Eclipse IDE

---

## 🧠 Core Java Concepts Demonstrated

### Object-Oriented Programming

* Classes and Objects
* Encapsulation
* Inheritance
* Polymorphism
* Abstraction
* Interfaces

### Collections

The application uses Java Collections for managing users, products, carts, orders, and other application data.

### Exception Handling

Exception handling is used to:

* Validate user input
* Handle invalid operations
* Handle duplicate records
* Handle invalid login attempts
* Handle file-related errors
* Handle business validation errors

### File Handling

Application data is stored permanently using `.dat` files.

### Serialization

Objects are serialized and deserialized to save and retrieve application data.

---

## ▶️ How to Run

### Requirements

* Java 17 or later
* Windows/Linux/macOS
* Command Prompt or Terminal

### Run from Source Code

Open the project in Eclipse and run:

```text
ECommerceShopping.java
```

as a Java Application.

### Run the Downloadable JAR

Download the latest release and extract the ZIP file.

The extracted folder should contain:

```text
EcommersRelease
│
├── EcommersRelease.jar
│
└── Database
    ├── users.dat
    ├── products.dat
    ├── carts.dat
    └── orders.dat
```

Open a terminal inside the `EcommersRelease` folder and run:

```bash
java -jar EcommersRelease.jar
```

The application will start with the main menu.

---

## 📋 Main Menu

```text
================================
        E-Commerce System
================================

1. Register
2. Login
3. Exit
```

After login, the application displays either the **User Menu** or **Admin Menu** depending on the authenticated account.

---

## 👤 User Menu

```text
================================
        Welcome User
================================

1. View All Products
2. Search Product
3. Add Product to Cart
4. View Cart
5. Remove Product from Cart
7. Place Order
8. Order History
9. My Profile
10. Logout
```

---

## 👨‍💼 Admin Menu

```text
================================
        Welcome Admin
================================

1. Add Product
2. Update Product
3. Delete Product
4. View All Products
5. Search Product
6. View All Users
7. View All Orders
10. Logout
```

---

## 📦 Data Files

```text
Database/
│
├── users.dat
├── products.dat
├── carts.dat
└── orders.dat
```

These files are used to maintain application data between application runs.

---

## 🎯 Project Objective

The main objective of this project is to gain practical experience in developing a real-world console application using Core Java.

The project demonstrates how different Java concepts can be combined to implement:

* Authentication
* Product management
* Shopping cart management
* Order processing
* Data persistence
* Business validation
* Exception handling
* Menu-driven application workflows

---

## 🔮 Future Enhancements

The project can be extended in the future with:

* Spring Boot REST APIs
* MySQL database
* React frontend
* JWT authentication
* Payment gateway integration
* Product images
* Order tracking
* Email notifications
* Cloud deployment

---

## 👨‍💻 Author

**Mohan Budhireddy**

Core Java | Java Backend Development | Spring Boot | MySQL

---

## 📄 License

This project is created for learning and educational purposes.

















What i learned in this 
jdbc - connection for backend and mysql , we install the connector-j (jar file) , we import it in the project right click on the project and building path - select the class path and import the external jar (connector-j)

jdbc connection steps to follow 

step -1
Connection con=DriverManger.GetConnection(url,username,password);// it will return Connection object
step -2
       con   - Statement, Prepare Statement, Callable Statement (this return the objects back);   with that object we will set the values to the query
step 3
   ResultSet  rs=    executeUpdate, executeQuery, execute
   
 step 4
   while(rs.next()){
   map the objects 
   }  
   
  step 5 - close the connections


indexes - go to mysql and in users and products table check the indexes you will see
    to execute the query faster , search the rows faster 
    read in the note book , 
    index uses the B-Tree internally
    index maintain the order
    
DAos - Data Acessing objects . dtos are interfaces acts the bridge between the mysql execution (daos implementation) and services class  , all curd operations are operated here.  
     
joins- most importent thing we have the multiple tables like cart,  cartitems .  when we want the data from multiple table we use the joins .
        By Using joins,  calls to the database will reduces and  appilcation will scallable 
 
** Transcations **  (CURD)
    if one operation is going on like
    User Ordered product 
      1- create the order row (save(orderid,orderstatus,....etc)) --create in mysal c- in curd operations
       2- products should add  to orderitems in db
       3-  delete the cart items 
       4 - delete cart 
    there are 4 operations in performing in the db
    in case if it fail in the 3 or 4 stage the changes in the database , should not be done we roll back
    
    - commit true/fase
    before starting the transncation we make the commit false 
   - roll back 
       undo type 
       -- save point 
           it rolback to the spefic point (upto to where you wanted to roll back)
           
 * Manuall Injuction dependency
 
    we create the Application context class for creating the objects and do manually injucation 
    when the starting the application it will create the object for the Application Context class, it will go it the class 
    create the objects in injucation dependency way - see the application class you will understand the dependency injucation 

    
       
 



