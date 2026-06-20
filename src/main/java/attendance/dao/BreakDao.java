package attendance.dao;

import attendance.dao.model.BreakRecord;
import java.util.ArrayList;

public interface BreakDao {
    ArrayList<BreakRecord> getByAttendanceId(int attendanceId);
    void create(int attendanceId, java.sql.Time breakStart, java.sql.Time breakEnd);
    void update(BreakRecord record);
    void deleteById(int id);
}
