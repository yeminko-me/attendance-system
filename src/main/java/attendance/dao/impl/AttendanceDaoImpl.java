package attendance.dao.impl;

import attendance.dao.AttendanceDao;
import attendance.dao.model.Attendance;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AttendanceDaoImpl extends BaseDao implements AttendanceDao {

    @Override
    public Attendance getTodayAttendance(int employeeId) {
        String sql = "SELECT id, employee_id, date, clock_in, clock_out, note FROM attendance WHERE employee_id=? AND date=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapAttendance(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Attendance getById(int id) {
        String sql = "SELECT id, employee_id, date, clock_in, clock_out, note FROM attendance WHERE id=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapAttendance(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Attendance> getByEmployee(int employeeId) {
        var list = new ArrayList<Attendance>();
        String sql = "SELECT id, employee_id, date, clock_in, clock_out, note FROM attendance WHERE employee_id=? ORDER BY date DESC";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAttendance(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Attendance> getByEmployeeAndMonth(int employeeId, int year, int month) {
        var list = new ArrayList<Attendance>();
        String sql = "SELECT id, employee_id, date, clock_in, clock_out, note FROM attendance WHERE employee_id=? AND YEAR(date)=? AND MONTH(date)=? ORDER BY date";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, year);
            stmt.setInt(3, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAttendance(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Attendance> getAll() {
        var list = new ArrayList<Attendance>();
        String sql = "SELECT id, employee_id, date, clock_in, clock_out, note FROM attendance ORDER BY date DESC";
        try (var stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapAttendance(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void clockIn(int employeeId) {
        String sql = "INSERT INTO attendance (employee_id, date, clock_in) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setTime(3, Time.valueOf(LocalTime.now()));
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void clockOut(int employeeId) {
        String sql = "UPDATE attendance SET clock_out=? WHERE employee_id=? AND date=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTime(1, Time.valueOf(LocalTime.now()));
            stmt.setInt(2, employeeId);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Attendance attendance) {
        String sql = "UPDATE attendance SET date=?, clock_in=?, clock_out=?, note=? WHERE id=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, attendance.date());
            stmt.setTime(2, attendance.clockIn());
            stmt.setTime(3, attendance.clockOut());
            stmt.setString(4, attendance.note());
            stmt.setInt(5, attendance.id());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        deleteById("attendance", id);
    }

    private Attendance mapAttendance(ResultSet rs) throws Exception {
        return new Attendance(
            rs.getInt("id"),
            rs.getInt("employee_id"),
            rs.getDate("date"),
            rs.getTime("clock_in"),
            rs.getTime("clock_out"),
            rs.getString("note")
        );
    }
}
