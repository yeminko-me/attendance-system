package attendance.dao;

import attendance.dao.model.Attendance;
import java.util.ArrayList;

public interface AttendanceDao {
    Attendance getTodayAttendance(int employeeId);
    Attendance getById(int id);
    ArrayList<Attendance> getByEmployee(int employeeId);
    ArrayList<Attendance> getByEmployeeAndMonth(int employeeId, int year, int month);
    ArrayList<Attendance> getAll();
    void clockIn(int employeeId);
    void clockOut(int employeeId);
    void update(Attendance attendance);
    void deleteById(int id);
}
