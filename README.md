# Attendance Management System

A Java SE desktop application with Swing GUI, Spring Boot REST API, and MySQL database for employee attendance tracking.

## Features

- **Check In / Check Out** — Record daily attendance with timestamps
- **Attendance Records** — View, filter (by year/month), edit, and delete records
- **Break Management** — Add/delete break times per attendance record, auto-calculates net working hours
- **Employee Management** — Admin can add/delete employees
- **Salary Calculation** — Calculate monthly salary based on net working hours × hourly rate
- **REST API** — Spring Boot endpoints for salary and break data

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | Java Swing (NetBeans GUI Builder .form) |
| REST API | Spring Boot 3.2.5 (JDK 17) |
| Database | MySQL 8.4 |
| Build | Maven |

## Getting Started

### Prerequisites

- JDK 17+
- MySQL 8.4
- Maven

### Setup

1. Create the database:
   ```
   mysql -u root -p < src/main/resources/attendance_schema.sql
   ```

2. Configure DB credentials in `src/main/java/attendance/dao/impl/DAO.java` (default: root / C@s38vxuaj)

3. Run Swing GUI:
   ```
   mvn compile exec:java
   ```

4. Run REST API (separate terminal):
   ```
   mvn spring-boot:run
   ```

### Default Admin Login

- **Username:** admin
- **Password:** admin123

## REST Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/salary?employeeId=&startDate=&endDate=&hourlyRate=` | Calculate monthly salary |
| GET | `/api/breaks/{attendanceId}` | List breaks for an attendance record |
| POST | `/api/breaks/{attendanceId}?breakStart=12:00&breakEnd=13:00` | Add a break |
| DELETE | `/api/breaks/{id}` | Delete a break |
| GET | `/api/breaks/net-hours/{attendanceId}` | Get net working hours for a record |

## Project Structure

```
src/main/java/attendance/
├── controller/     # Spring REST controllers
├── dao/            # Data access layer (interfaces + JDBC impl)
│   ├── impl/
│   └── model/      # Records (Employee, Attendance, BreakRecord)
├── dto/            # Data transfer objects
├── service/        # Business logic
│   └── impl/
└── ui/             # Swing UI (generated + hand-written)
```
