package attendance.service;

import attendance.dao.model.Employee;

public class App {
    private static App singleton;
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final BreakService breakService;
    private Employee currentUser;

    private App() {
        this.employeeService = new attendance.service.impl.EmployeeServiceImpl();
        this.attendanceService = new attendance.service.impl.AttendanceServiceImpl();
        this.breakService = new attendance.service.impl.BreakServiceImpl();
    }

    public static App getSingleton() {
        if (singleton == null) {
            singleton = new App();
        }
        return singleton;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public AttendanceService getAttendanceService() {
        return attendanceService;
    }

    public BreakService getBreakService() {
        return breakService;
    }

    public Employee getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && "admin".equals(currentUser.role());
    }
}
