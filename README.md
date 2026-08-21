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
