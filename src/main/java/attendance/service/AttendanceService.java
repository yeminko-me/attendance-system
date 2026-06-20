package attendance.service;

import attendance.dao.model.Attendance;
import java.util.ArrayList;

public interface AttendanceService {
    Attendance getTodayAttendance(int employeeId);
    Attendance getById(int id);
    ArrayList<Attendance> getByEmployee(int employeeId);
    ArrayList<Attendance> getByEmployeeAndMonth(int employeeId, int year, int month);
    ArrayList<Attendance> getAll();
    boolean clockIn(int employeeId);
    boolean clockOut(int employeeId);
    void update(Attendance attendance);
    void deleteById(int id);
}
