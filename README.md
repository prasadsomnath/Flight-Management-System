# Flight-Management-System
📋 Project Description
The Flight Management System is a Java-based application that allows users to register, searching for flights, 
booking of  tickets, cancel bookings, and view their reservations. It uses JDBC to connect to a MySQL database, 
managing all flight and user data efficiently.

🛠️ Technologies Used
- Java (Core Java)
- JDBC (Java Database Connectivity)
- MySQL Database
- intellij
- Git & GitHub

📚 Features
- User Registration
- Flight Search based on source and destination
- Flight Booking for registered users
- Cancel Flight Booking
- View Bookings made by the user
- Check User ID existence
-Exit Application

🗄️ Database Structure
- users table: Stores user details (user_id, name, email, number)

- flights table: Stores flight details (flight_id, departure, destination, date, no_of_seats,price)

- booking table: Stores booking records (booking_id, user_id, flight_id, seat_number,status)

🚀 How to Run
- Clone the repository:
- git clone https://github.com/prasadsomnath/Flight-Management-System.git

## Set up the MySQL database:
create database with name my_db.
Create the required tables (users, flights, booking).
Update your database credentials in the Java code.

## Compile and run the project:
javac app.java
java Main

📂 Project Structur:

- Flight-Management-System
  -  ├── src/
    -      └── Main
      -         └── java
        -             └── com.skyllx(package)
          -                   └──app.java
├── README.md
└── schema.sql 

📢 Notes:
- Ensure you have MySQL server running before executing the application.
- JDBC Driver (Connector/J) must be configured in your project setup.

📄 License :
This project is for educational purposes only.
