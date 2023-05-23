# UrbanOven
UrbanOven is a Java console application that allows users to register for an account and order pizza based on their preferences. The application has a predefined list of pizzas and drinks, with toppings that can be modified to suit the user's liking. The application also allows users to view their order history, as well as employees to view all orders, find orders by order ID and username, and filter orders by date.

## Usage
To use UrbanOven, follow these steps:  
1. Open a command prompt or terminal windows and navigate to the directory where you downloaded the UrbanOven source code.
2. Navigate to java folder with: `cd ./src/main/java`
3. Compile the source code by typing `javac main/Main.java`
4. Run the application by typing `java main.Main`
5. If you are a new user, you will need to register for an account by selecting option 2 from the main menu and following the prompts.
6. Once you have registered, you can log in to your account by selecting option 1 from the main menu and entering your username and password.
7. From the main menu, you can create a new order by selecting option 1 and following the prompts.
8. You can view your order history by selecting option 2 from the user menu or view the pizzeria menu by typing 3.
9. If you are an employee, you can view all orders by selecting option 1, find orders by order ID or username by selecting option 2, filter orders by date by selecting option 3, update the order status by selecting option 4, add new employee by selecting option 5, update employee salary by selecting option 6, update employee ranking by selecting option 7, and deleting an employee for the system by selecting option 8.

## Predefined Users
The application comes with two predefined users for testing purposes:
* Customer profile with username `user` and password `user`
* Employee profile with username `admin` and password `admin`

## Database Setup
UrbanOven utilizes a MySQL database to store and manage data. To set up the database and establish a connection with the application, please follow the instructions below:
1. Install MySQL and MySQL Workbench if you haven't already. You can download them from the official MySQL website: `https://www.mysql.com/downloads/`
2. Open MySQL Workbench and connect to your local or remote MySQL server.
3. Create a new schema (database) for UrbanOven. You can do this by executing the following SQL command: `CREATE DATABASE urbanoven;`
4. The necessary tables will be created automatically, by the script, in the `init.sql`
5. Before the first start, you can configure your database connection settings located in the main file:
    * **Driver**: Set the name of the app database (default: mysql).
    * **Port**: Specify the port number on which MySQL is running (default: 3306).
    * **Database**: Set the name of the UrbanOven database/schema. (default: urbanoven).
    * **Username**: Provide the username with the necessary privileges to access the UrbanOven database.
    * **Password**: Enter the corresponding password for the specified username.  
  
These instructions assume a basic understanding of MySQL and database management. If you encounter any issues during the setup process, refer to the official MySQL documentation or seek assistance from the MySQL community.
