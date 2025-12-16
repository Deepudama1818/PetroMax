⛽ PetroMax – On-Demand Fuel Delivery Web Application

PetroMax is a Java-based full-stack web application that enables users to order petrol or diesel for their vehicles and get it delivered to their location.
The application follows a multi-step order workflow using JSP + Servlets + JDBC, styled with a premium dark UI, and backed by MySQL.

📌 Problem Statement

Traditional fuel refilling requires users to visit petrol bunks physically. PetroMax solves this problem by allowing users to:

Order fuel remotely

Select fuel type and quantity

Provide delivery location

Preview order details

Confirm delivery with rider information

🏗️ System Architecture
Browser (HTML / JSP)
        ↓
Servlets (Controller Layer)
        ↓
JDBC (Database Access Layer)
        ↓
MySQL Database

🛠️ Technology Stack
Layer	Technology
Frontend	HTML, CSS, JSP
Backend	Java, Servlets (Jakarta API)
Database	MySQL
Build Tool	Maven
Server	Apache Tomcat 10
Version Control	Git & GitHub
📂 Project Structure (Maven Standard)
PetroMax
├── src
│   └── main
│       ├── java
│       │   └── com.petromax
│       │       ├── LoginServlet.java
│       │       ├── RegisterServlet.java
│       │       ├── OrderServlet.java
│       │       └── DBConnection.java
│       └── webapp
│           ├── login.html
│           ├── order.jsp
│           └── WEB-INF
│               └── web.xml
├── pom.xml
└── README.md

🔐 User Authentication Module
Login Flow

Validates username and password

Fetches user-specific data (vehicle type)

Stores user details in session

LoginServlet.java (Core Logic)
PreparedStatement ps = con.prepareStatement(
    "SELECT vehicle_type FROM users WHERE username=? AND password=?"
);

ps.setString(1, username);
ps.setString(2, password);

ResultSet rs = ps.executeQuery();

if (rs.next()) {
    HttpSession session = request.getSession();
    session.setAttribute("username", username);
    session.setAttribute("vehicleType", rs.getString("vehicle_type"));
    response.sendRedirect("order.jsp");
}


✔ Session-based authentication
✔ No repeated DB calls

📝 User Registration Module

New users provide:

Username

Password

Vehicle number

Vehicle type (2W / 4W)

Database Insert (RegisterServlet)
PreparedStatement ps = con.prepareStatement(
    "INSERT INTO users(username,password,vehicle_no,vehicle_type) VALUES(?,?,?,?)"
);

ps.setString(1, username);
ps.setString(2, password);
ps.setString(3, vehicleNo);
ps.setString(4, vehicleType);

ps.executeUpdate();

⛽ Order Management Module

The order process is handled in a single JSP page (order.jsp) using step-based navigation.

Order Steps
Vehicle → Bunk → Fuel → Location → Preview → Confirmation

💰 Pricing Logic
Fuel Type	Price / Litre
Petrol	₹110
Diesel	₹90

Additional charges:

Delivery Charge: ₹50

Tax: ₹30

Price Calculation (OrderServlet)
int pricePerLitre = fuel.equals("Petrol") ? 110 : 90;
int fuelCost = pricePerLitre * quantity;
int totalAmount = fuelCost + 50 + 30;

session.setAttribute("fuelCost", fuelCost);
session.setAttribute("totalAmount", totalAmount);

👀 Order Preview Page

Before confirmation, the user sees:

Vehicle number & type

Fuel details

Quantity

Charges breakdown

Delivery location

Mobile number

Payment mode (Cash on Delivery)

JSP Snippet
<p><strong>Fuel:</strong> <%= session.getAttribute("fuel") %></p>
<p><strong>Quantity:</strong> <%= session.getAttribute("quantity") %> L</p>
<p><strong>Total Amount:</strong> ₹ <%= session.getAttribute("totalAmount") %></p>


✔ Prevents incorrect orders
✔ Improves user experience

✅ Order Confirmation

After confirmation:

Order is stored in the database

Rider details are shown

Delivery status is displayed

Sample Rider Info (UI)
Driver Name: Ramesh Kumar
Rating: ⭐ 4.6 / 5
Contact: +91 7670867303

🗄️ Database Design
Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    vehicle_no VARCHAR(20),
    vehicle_type VARCHAR(10)
);

Orders Table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    vehicle_no VARCHAR(20),
    vehicle_type VARCHAR(10),
    bunk VARCHAR(50),
    fuel VARCHAR(10),
    quantity INT,
    fuel_cost INT,
    delivery_charge INT,
    tax INT,
    total_amount INT,
    payment_mode VARCHAR(20),
    location VARCHAR(100),
    mobile VARCHAR(10),
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

⚙️ How to Run Locally
git clone https://github.com/your-username/PetroMax.git
cd PetroMax
mvn clean package


Deploy PetroMax-1.0.war into Tomcat webapps/.

Access:

http://localhost:8080/PetroMax-1.0

🔐 Security & Validation

Session-based authentication

PreparedStatements (SQL Injection safe)

Mobile number validation (10 digits)

Mandatory field validation at each step

🚀 Future Enhancements

Online payment gateway

OTP verification

Admin dashboard

Order tracking with maps

Multiple vehicles per user

👨‍💻 Author

Sai Deepak D Y
Technical Trainer
(Iamneo Project)

📜 License

This project is developed for academic and learning purposes.