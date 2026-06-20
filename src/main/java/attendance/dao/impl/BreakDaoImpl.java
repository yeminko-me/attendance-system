package attendance.dao.impl;

import attendance.dao.BreakDao;
import attendance.dao.model.BreakRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

public class BreakDaoImpl extends BaseDao implements BreakDao {

    @Override
    public ArrayList<BreakRecord> getByAttendanceId(int attendanceId) {
        var list = new ArrayList<BreakRecord>();
        String sql = "SELECT id, attendance_id, break_start, break_end FROM break_record WHERE attendance_id=? ORDER BY break_start";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapBreak(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void create(int attendanceId, Time breakStart, Time breakEnd) {
        String sql = "INSERT INTO break_record (attendance_id, break_start, break_end) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            stmt.setTime(2, breakStart);
            stmt.setTime(3, breakEnd);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(BreakRecord record) {
        String sql = "UPDATE break_record SET break_start=?, break_end=? WHERE id=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTime(1, record.breakStart());
            stmt.setTime(2, record.breakEnd());
            stmt.setInt(3, record.id());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        deleteById("break_record", id);
    }

    private BreakRecord mapBreak(ResultSet rs) throws Exception {
        return new BreakRecord(
            rs.getInt("id"),
            rs.getInt("attendance_id"),
            rs.getTime("break_start"),
            rs.getTime("break_end")
        );
    }
}
