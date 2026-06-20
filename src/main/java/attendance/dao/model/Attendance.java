package attendance.dao.model;

import java.sql.Time;
import java.sql.Date;

public record Attendance(int id, int employeeId, Date date, Time clockIn, Time clockOut, String note) {}
