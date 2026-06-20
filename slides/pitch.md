---
marp: true
theme: default
paginate: true
auto-advance: 20
---

# Attendance System

**Employee Management & Time Tracking**

Java Swing + MySQL

---

## The Problem

- Manual attendance tracking is error-prone
- Paper-based systems are hard to audit
- Salary calculation takes hours of manual work

---

## Our Solution

- **Employee Management** — register, view, edit employees
- **Clock In/Out** — daily attendance with timestamps
- **Break Tracking** — log breaks, calculate net working hours
- **Salary Calculation** — automated pay based on hours worked

---

## Architecture

```
UI (Swing) → Controller → Service → DAO → MySQL
```

- MVC pattern with clear layer separation
- DAO layer uses PreparedStatements
- Service layer handles business logic & validation

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | Java Swing + IntelliJ GUI Designer |
| Backend | Java 17, Maven |
| Database | MySQL 8 |
| Tooling | Claude Code, MCP MySQL server |

---

## Demo & Next Steps

- Live demo of clock-in, break tracking, salary report
- **Next**: REST API layer, web frontend, role-based access

🔗 **github.com/yeminko-me/attendance-system**
