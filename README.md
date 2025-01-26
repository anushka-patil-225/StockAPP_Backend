"# StockAPP_Backend" 



# Portfolio Tracker

A responsive web application to manage stock holdings, track real-time stock prices, and dynamically calculate portfolio value. Built with **React.js** for the frontend, **Java Spring Boot** for the backend, and **MySQL** for database management.

---

## Features

- **User-Friendly Interface:** A clean and responsive frontend for managing stock holdings.
- **Real-Time Stock Prices:** Integration with APIs for fetching live stock prices.
- **CRUD Operations:** Add, update, delete, and view stock holdings.
- **Portfolio Analytics:** Automatic calculation of total portfolio value.
- **Database-Driven Design:** MySQL database for secure and efficient data management.

---

## Steps to Run the Project Locally

### Prerequisites

1. Node.js and npm installed on your system.
2. Java Development Kit (JDK) 8+ installed.
3. MySQL server installed and running.
4. Maven installed (optional, if using Maven for backend setup).

---

### Setup Instructions

#### 1. Clone the Repository
```bash
git clone https://github.com/anushka-patil-225/StockApp.git
cd StockApp
```

#### 2. Setting Up the Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Open the `application.properties` file in the `src/main/resources` directory and update the MySQL database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/portfolio_tracker
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   ```
3. Create the database schema:
   ```sql
   CREATE DATABASE portfolio_tracker;
   ```
4. Build and run the backend:
   ```bash
   ./mvnw spring-boot:run
   ```

#### 3. Setting Up the Frontend

1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```



