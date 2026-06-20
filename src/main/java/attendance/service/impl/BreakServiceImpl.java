package attendance.service.impl;

import attendance.dao.BreakDao;
import attendance.dao.impl.BreakDaoImpl;
import attendance.dao.model.BreakRecord;
import attendance.service.BreakService;
import java.sql.Time;
import java.util.ArrayList;

public class BreakServiceImpl implements BreakService {

    private final BreakDao dao = new BreakDaoImpl();

    @Override
    public ArrayList<BreakRecord> getByAttendanceId(int attendanceId) {
        return dao.getByAttendanceId(attendanceId);
    }

    @Override
    public void create(int attendanceId, Time breakStart, Time breakEnd) {
        dao.create(attendanceId, breakStart, breakEnd);
    }

    @Override
    public void update(BreakRecord record) {
        dao.update(record);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
