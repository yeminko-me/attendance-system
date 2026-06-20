# Java MVC Pattern Skill

This skill enforces consistent MVC architecture across the attendance system codebase.

## Rules

### Controllers
- Controllers handle HTTP/UI input only — no business logic
- Delegate all work to service classes
- Name pattern: `*Controller.java`
- Place in `attendance.controller` package

### Services
- Services contain business logic and validation
- Services call DAOs for data access — never bypass to DB directly
- Name pattern: `*Service.java` with `*ServiceImpl.java` for implementation
- Place in `attendance.service` and `attendance.service.impl` packages

### DAOs
- DAOs handle all database operations (CRUD)
- Use prepared statements to prevent SQL injection
- Name pattern: `*Dao.java` with `*DaoImpl.java` for implementation
- Place in `attendance.dao` and `attendance.dao.impl` packages

### Models / DTOs
- Models map to database tables (e.g., `Employee`, `Attendance`)
- DTOs transfer data between layers (e.g., `SalaryResponse`, `BreakTime`)
- Place in `attendance.dao.model` and `attendance.dto` packages

### UI (Swing)
- Use `.form` files alongside `.java` for IntelliJ GUI Designer
- Keep UI code in `attendance.ui` package
- Never put business logic in UI event handlers — call services instead

## File Naming
- PascalCase for class names matching file names
- Package names are lowercase
- SQL schemas in `src/main/resources/`
