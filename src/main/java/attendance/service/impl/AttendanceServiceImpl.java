package attendance.service.impl;

import attendance.dao.AttendanceDao;
import attendance.dao.impl.AttendanceDaoImpl;
import attendance.dao.model.Attendance;
import attendance.service.AttendanceService;
import java.util.ArrayList;

public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceDao dao = new AttendanceDaoImpl();

    @Override
    public Attendance getTodayAttendance(int employeeId) {
        return dao.getTodayAttendance(employeeId);
    }

    @Override
    public Attendance getById(int id) {
        return dao.getById(id);
    }

    @Override
    public ArrayList<Attendance> getByEmployee(int employeeId) {
        return dao.getByEmployee(employeeId);
    }

    @Override
    public ArrayList<Attendance> getByEmployeeAndMonth(int employeeId, int year, int month) {
        return dao.getByEmployeeAndMonth(employeeId, year, month);
    }

    @Override
    public ArrayList<Attendance> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean clockIn(int employeeId) {
        try {
            dao.clockIn(employeeId);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean clockOut(int employeeId) {
        try {
            dao.clockOut(employeeId);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Attendance attendance) {
        dao.update(attendance);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
