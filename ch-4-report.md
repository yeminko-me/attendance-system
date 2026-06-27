# Chapter 4: System Implementation

## 4.1 Overview

The Attendance Management System is a Java SE desktop application integrated with a Spring Boot REST API and MySQL database. It follows a **3-layer architecture**: Presentation (Swing UI) → Business Logic (Service) → Data Access (DAO) → MySQL.

## 4.2 Technology Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| Frontend | Java Swing (NetBeans GUI Builder) | Desktop user interface |
| REST API | Spring Boot 3.2.5 (JDK 17) | HTTP endpoints for external integration |
| Database | MySQL 8.4 | Data persistence |
| Build | Maven | Dependency management and build automation |
| Security | SHA-256 password hashing | Credential storage |

## 4.3 System Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Swing UI Layer                     │
│  LoginUI / MainUI / AttendanceScreenUI /             │
│  SalaryScreenUI / EmployeeManageUI / RegisterUI      │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────┐
│                 Service Layer                        │
│  App (Singleton) → EmployeeService / AttendanceService/ │
│                    BreakService / SalaryService       │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────┐
│                  DAO Layer (JDBC)                    │
│  EmployeeDaoImpl / AttendanceDaoImpl / BreakDaoImpl  │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────┴──────────────────────────────┐
│                    MySQL Database                    │
│  employee / attendance / break_record                │
└─────────────────────────────────────────────────────┘
```

## 4.4 Database Design

### 4.4.1 Entity Relationship Diagram

```
┌───────────────┐       ┌──────────────────┐       ┌───────────────┐
│   employee    │       │   attendance     │       │  break_record │
├───────────────┤       ├──────────────────┤       ├───────────────┤
│ id (PK)       │──┐    │ id (PK)          │──┐    │ id (PK)       │
│ username      │  │    │ employee_id (FK) │  │    │ attendance_id │
│ password(SHA2)│  │──→ │ date             │  │──→ │ break_start   │
│ full_name     │  │    │ clock_in         │  │    │ break_end     │
│ role          │  │    │ clock_out        │  │    └───────────────┘
│ hourly_rate   │  │    │ note             │  │
│ address       │  │    └──────────────────┘  │
│ phone         │  │                          │
└───────────────┘  └──────────────────────────┘
```

### 4.4.2 Table: employee

| Column | Type | Description |
|--------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique employee ID |
| username | VARCHAR(50) (UNIQUE) | Login username |
| password | VARCHAR(64) | SHA-256 hashed password |
| full_name | VARCHAR(100) | Employee full name |
| role | VARCHAR(10) | 'admin' or 'user' |
| hourly_rate | DECIMAL(10,2) | Default: 1000.00 |
| address | VARCHAR(255) | Employee address |
| phone | VARCHAR(20) | Phone number |
| created_at | TIMESTAMP | Auto-generated |

### 4.4.3 Table: attendance

| Column | Type | Description |
|--------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique record ID |
| employee_id | INT (FK → employee.id) | References employee |
| date | DATE | Attendance date |
| clock_in | TIME | Check-in time |
| clock_out | TIME | Check-out time |
| note | VARCHAR(255) | Optional note |

### 4.4.4 Table: break_record

| Column | Type | Description |
|--------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique break ID |
| attendance_id | INT (FK → attendance.id) | References attendance |
| break_start | TIME | Break start time |
| break_end | TIME | Break end time |

## 4.5 Key Implementation Details

### 4.5.1 Singleton Service Locator

The `App` class acts as a central service locator using the Singleton pattern. It provides access to all services and maintains the current user session.

```java
public class App {
    private static App singleton;
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final BreakService breakService;
    private Employee currentUser;

    private App() {
        this.employeeService = new EmployeeServiceImpl();
        this.attendanceService = new AttendanceServiceImpl();
        this.breakService = new BreakServiceImpl();
    }

    public static App getSingleton() { ... }
    public boolean isAdmin() {
        return currentUser != null && "admin".equals(currentUser.role());
    }
}
```

### 4.5.2 Net Working Hours Calculation

The `SalaryService.calculateNetWorkingHours()` method computes actual working time by subtracting break durations from total attendance time.

```java
public NetWorkingHours calculateNetWorkingHours(
        LocalDateTime checkIn, LocalDateTime checkOut, List<BreakTime> breaks) {
    Duration totalAttendance = Duration.between(checkIn, checkOut);
    Duration totalBreaks = Duration.ZERO;
    for (BreakTime b : breaks) {
        totalBreaks = totalBreaks.plus(Duration.between(b.breakStart(), b.breakEnd()));
    }
    Duration net = totalAttendance.minus(totalBreaks);
    if (net.isNegative()) net = Duration.ZERO;

    long hours = net.toHours();
    long minutes = net.toMinutesPart();
    String formatted = String.format("%d Hours %d Minutes", hours, minutes);
    double decimalHours = net.toMinutes() / 60.0;
    return new NetWorkingHours(formatted, decimalHours);
}
```

### 4.5.3 Secure Password Storage

Passwords are hashed using MySQL's `SHA2()` function with 256-bit SHA-256. This ensures plaintext passwords are never stored in the database.

```sql
INSERT INTO employee (username, password, full_name, role)
VALUES (?, SHA2(?, 256), ?, ?);
```

## 4.6 REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/salary?employeeId=&startDate=&endDate=&hourlyRate=` | Calculate monthly salary |
| GET | `/api/breaks/{attendanceId}` | List breaks for an attendance record |
| POST | `/api/breaks/{attendanceId}?breakStart=12:00&breakEnd=13:00` | Add a break |
| DELETE | `/api/breaks/{id}` | Delete a break |
| GET | `/api/breaks/net-hours/{attendanceId}` | Get net working hours |

## 4.7 User Interface Screens

### Login Screen
- Username and password input fields
- SHA-256 authentication against database
- Register button for new user registration

### Main Dashboard
- Welcome message with current user name
- Current date and check-in status display
- Check In / Check Out buttons (context-sensitive enable/disable)
- Admin-only buttons: Manage Employees, Calculate Salary

### Attendance Records Screen
- Table view with columns: ID, Date, Check In, Check Out, Net Hours, Note, Employee
- Filter by year/month
- Edit check-in/out times and notes (admin only)
- Delete records (admin only)
- Break management dialog (Add/Delete breaks)

### Employee Management Screen (Admin)
- Table view: ID, Username, Full Name, Role, Address, Phone
- Add new employee with username, password, full name, address, phone
- Edit existing employee details and role
- Delete employee with cascade deletion of attendance records

### Salary Calculation Screen (Admin)
- Select employee, year, month, and hourly rate
- Daily breakdown: Date, In, Out, Gross Hours, Break Hours, Net Hours
- Total net hours and calculated salary

## 4.8 Application Flow

```
Login → Main Dashboard
         ├── Check In / Check Out
         ├── View Attendance Records
         │      ├── Filter by Year/Month
         │      ├── Edit Record (Admin)
         │      ├── Delete Record (Admin)
         │      └── Manage Breaks (Add/Delete)
         ├── Manage Employees (Admin)
         │      ├── Add Employee
         │      ├── Edit Employee
         │      └── Delete Employee
         ├── Calculate Salary (Admin)
         │      └── View Daily Breakdown
         └── Logout → Back to Login
```

## 4.9 Security Features

1. **Password Hashing**: SHA-256 via MySQL `SHA2()` function
2. **Role-Based Access**: Admin-only features (edit, delete, manage employees, salary)
3. **Prepared Statements**: All SQL queries use parameterized statements to prevent SQL injection
4. **Session Management**: `currentUser` in singleton maintains login state
5. **Validation**: Check-in/out time validation (out must be after in), break time validation

## 4.10 Testing and Deployment

- **Run Swing GUI**: `mvn compile exec:java`
- **Run REST API**: `mvn spring-boot:run` (port 8080)
- **Database Setup**: Execute `attendance_schema.sql` against MySQL
- **Default Credentials**: admin / admin123
