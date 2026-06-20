package attendance.dao.model;

import java.sql.Time;

public record BreakRecord(int id, int attendanceId, Time breakStart, Time breakEnd) {}
