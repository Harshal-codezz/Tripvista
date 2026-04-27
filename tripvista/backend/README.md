# TripVista Backend — Java Servlets + MySQL

A complete backend for the TripVista travel booking website built with **Java Servlets**, **JDBC**, and **MySQL**.

---

## 📋 Prerequisites

Before setting up, make sure you have the following installed:

| Software | Version | Download Link |
|----------|---------|--------------|
| **Java JDK** | 11 or higher | [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) |
| **Apache Maven** | 3.6+ | [Download Maven](https://maven.apache.org/download.cgi) |
| **Apache Tomcat** | 9.0 or 10.0 | [Download Tomcat](https://tomcat.apache.org/download-90.cgi) |
| **MySQL Server** | 8.0+ | [Download MySQL](https://dev.mysql.com/downloads/mysql/) |
| **MySQL Workbench** *(optional)* | 8.0+ | [Download Workbench](https://dev.mysql.com/downloads/workbench/) |

---

## 🚀 Setup Instructions (Step by Step)

### Step 1: Install Java JDK

1. Download and install JDK 11+ from Oracle or use OpenJDK
2. Set `JAVA_HOME` environment variable:
   - **Windows**: 
     - Search "Environment Variables" in Start Menu
     - Add new System Variable: `JAVA_HOME` = `C:\Program Files\Java\jdk-11.x.x`
     - Add `%JAVA_HOME%\bin` to the `Path` variable
3. Verify installation:
   ```bash
   java -version
   javac -version
   ```

### Step 2: Install Apache Maven

1. Download Maven from https://maven.apache.org/download.cgi
2. Extract to a folder (e.g., `C:\apache-maven-3.9.x`)
3. Add Maven to PATH:
   - Add System Variable: `MAVEN_HOME` = `C:\apache-maven-3.9.x`
   - Add `%MAVEN_HOME%\bin` to the `Path` variable
4. Verify installation:
   ```bash
   mvn -version
   ```

### Step 3: Install & Configure MySQL

1. Download and install MySQL Server 8.0+ from https://dev.mysql.com/downloads/mysql/
2. During installation, set a root password (remember it!)
3. Start MySQL service:
   - **Windows**: MySQL should run as a service automatically
   - Or start manually: `net start mysql80`
4. Open MySQL command line or MySQL Workbench

### Step 4: Create the Database

1. Open a terminal/command prompt and connect to MySQL:
   ```bash
   mysql -u root -p
   ```
   Enter your password when prompted.

2. Run the schema file to create the database, tables, and sample data:
   ```bash
   mysql -u root -p < sql/tripvista_schema.sql
   ```
   
   **OR** if you're inside the MySQL shell:
   ```sql
   SOURCE sql/tripvista_schema.sql;
   ```

3. Verify the database was created:
   ```sql
   USE tripvista_db;
   SHOW TABLES;
   ```
   You should see 8 tables: `users`, `flights`, `hotels`, `trains`, `buses`, `packages`, `bookings`, `newsletter`

### Step 5: Update Database Credentials

Open `src/main/java/com/tripvista/config/DBConnection.java` and update:

```java
private static final String DB_USER = "root";           // Your MySQL username
private static final String DB_PASSWORD = "";            // Your MySQL password
```

> ⚠️ If your MySQL password is not empty, you MUST update `DB_PASSWORD` to your actual password.

### Step 6: Install Apache Tomcat

1. Download Tomcat 9 from https://tomcat.apache.org/download-90.cgi
2. Extract to a folder (e.g., `C:\apache-tomcat-9.0.x`)
3. Set environment variable: `CATALINA_HOME` = `C:\apache-tomcat-9.0.x`

### Step 7: Build the Project

1. Open a terminal in the `backend/` directory:
   ```bash
   cd "a:\Web project sem-6\tripvista\backend"
   ```

2. Build the project using Maven:
   ```bash
   mvn clean package
   ```

3. This will create `target/tripvista.war` — this is your deployable WAR file.

### Step 8: Deploy to Tomcat

**Option A — Copy WAR file:**
1. Copy `target/tripvista.war` to Tomcat's `webapps/` folder:
   ```bash
   copy target\tripvista.war C:\apache-tomcat-9.0.x\webapps\
   ```

2. Start Tomcat:
   ```bash
   C:\apache-tomcat-9.0.x\bin\startup.bat
   ```

**Option B — Use Maven Tomcat Plugin (Advanced):**
```bash
mvn tomcat7:run
```

### Step 9: Test the APIs

Once Tomcat is running, the backend will be available at `http://localhost:8080/tripvista/`

Test these endpoints in your browser or Postman:

| Method | URL | Description |
|--------|-----|-------------|
| `GET` | `http://localhost:8080/tripvista/api/flights` | Get all flights |
| `GET` | `http://localhost:8080/tripvista/api/flights?from=Delhi&to=Mumbai` | Search flights |
| `GET` | `http://localhost:8080/tripvista/api/hotels` | Get all hotels |
| `GET` | `http://localhost:8080/tripvista/api/hotels?city=Goa` | Search hotels by city |
| `GET` | `http://localhost:8080/tripvista/api/trains` | Get all trains |
| `GET` | `http://localhost:8080/tripvista/api/trains?from=Delhi&to=Mumbai` | Search trains |
| `GET` | `http://localhost:8080/tripvista/api/buses` | Get all buses |
| `GET` | `http://localhost:8080/tripvista/api/buses?from=Pune&to=Mumbai` | Search buses |
| `GET` | `http://localhost:8080/tripvista/api/packages` | Get all packages |
| `GET` | `http://localhost:8080/tripvista/api/packages?destination=Goa` | Search packages |
| `POST` | `http://localhost:8080/tripvista/api/register` | Register user |
| `POST` | `http://localhost:8080/tripvista/api/login` | Login user |
| `POST` | `http://localhost:8080/tripvista/api/bookings` | Create booking |
| `GET` | `http://localhost:8080/tripvista/api/bookings` | Get user bookings |
| `POST` | `http://localhost:8080/tripvista/api/newsletter` | Subscribe to newsletter |

### Example: Test Registration (using Postman or curl)

```bash
curl -X POST http://localhost:8080/tripvista/api/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Test User","email":"test@example.com","password":"test123","phone":"9876543210"}'
```

Expected response:
```json
{
  "success": true,
  "message": "Account created successfully! Please sign in."
}
```

### Example: Test Login

```bash
curl -X POST http://localhost:8080/tripvista/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"arjun@example.com","password":"password123"}'
```

Expected response:
```json
{
  "success": true,
  "message": "Login successful!",
  "user": {
    "id": 1,
    "fullName": "Arjun Mehta",
    "email": "arjun@example.com",
    "phone": "9876543210"
  }
}
```

---

## 📁 Project Structure

```
backend/
├── pom.xml                              # Maven build config
├── sql/
│   └── tripvista_schema.sql             # Database schema + sample data
├── src/main/java/com/tripvista/
│   ├── config/
│   │   └── DBConnection.java            # MySQL JDBC connection
│   ├── filter/
│   │   └── CORSFilter.java              # Cross-origin request filter
│   ├── model/                           # POJO classes
│   │   ├── User.java
│   │   ├── Flight.java
│   │   ├── Hotel.java
│   │   ├── Train.java
│   │   ├── Bus.java
│   │   ├── HolidayPackage.java
│   │   ├── Booking.java
│   │   └── Newsletter.java
│   ├── dao/                             # Data Access Objects (JDBC)
│   │   ├── UserDAO.java
│   │   ├── FlightDAO.java
│   │   ├── HotelDAO.java
│   │   ├── TrainDAO.java
│   │   ├── BusDAO.java
│   │   ├── PackageDAO.java
│   │   ├── BookingDAO.java
│   │   └── NewsletterDAO.java
│   └── servlet/                         # Controllers
│       ├── LoginServlet.java
│       ├── RegisterServlet.java
│       ├── FlightSearchServlet.java
│       ├── HotelSearchServlet.java
│       ├── TrainSearchServlet.java
│       ├── BusSearchServlet.java
│       ├── PackageSearchServlet.java
│       ├── BookingServlet.java
│       └── NewsletterServlet.java
└── src/main/webapp/WEB-INF/
    └── web.xml                          # Servlet deployment descriptor
```

---

## 🔧 Troubleshooting

### "MySQL JDBC Driver not found"
- Make sure `mysql-connector-java` is in your `pom.xml` dependencies
- Run `mvn clean package` again

### "Access denied for user 'root'"
- Update `DB_PASSWORD` in `DBConnection.java` to your actual MySQL root password

### "Port 8080 already in use"
- Change Tomcat's port in `conf/server.xml` (change `8080` to `8081` or another port)
- Or stop whatever is using port 8080: `netstat -ano | findstr 8080`

### "404 Not Found on API endpoints"
- Make sure the WAR file is named `tripvista.war` (context path = `/tripvista`)
- Check Tomcat logs in `logs/catalina.out` for errors
- Verify web.xml servlet mappings match the URLs you're accessing

### "Database connection error"
- Verify MySQL is running: `mysql -u root -p`
- Verify database exists: `SHOW DATABASES;` should list `tripvista_db`
- Check JDBC URL in `DBConnection.java`

---

## 📝 Architecture Notes

This backend follows the **MVC (Model-View-Controller)** pattern:

- **Model** (`model/` package) — Java POJOs representing database entities
- **View** — The existing HTML/CSS/JS frontend
- **Controller** (`servlet/` package) — Servlets that handle HTTP requests and return JSON responses

The **DAO (Data Access Object)** pattern separates database logic from business logic, making the code modular and testable.

All API responses use **JSON format** via Google's Gson library, making it easy for the JavaScript frontend to consume.

---

## 📌 Sample Users for Testing

| Name | Email | Password |
|------|-------|----------|
| Arjun Mehta | arjun@example.com | password123 |
| Priya Sharma | priya@example.com | password123 |
| Ravi Kumar | ravi@example.com | password123 |
