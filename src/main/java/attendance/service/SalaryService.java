package attendance.service;

import attendance.dao.model.Attendance;
import attendance.dao.model.BreakRecord;
import attendance.dao.model.Employee;
import attendance.dto.BreakTime;
import attendance.dto.NetWorkingHours;
import attendance.dto.SalaryResponse;
import attendance.dto.SalaryResponse.DailyDetail;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalaryService {

    private final App app = App.getSingleton();

    public NetWorkingHours calculateNetWorkingHours(LocalDateTime checkIn, LocalDateTime checkOut, List<BreakTime> breaks) {
        Duration totalAttendance = Duration.between(checkIn, checkOut);
        Duration totalBreaks = Duration.ZERO;
        for (BreakTime b : breaks) {
            totalBreaks = totalBreaks.plus(Duration.between(b.breakStart(), b.breakEnd()));
        }
        Duration net = totalAttendance.minus(totalBreaks);
        if (net.isNegative()) net = Duration.ZERO;

        long hours = net.toHours();
        long minutes = net.toMinutesPart();
        String formatted = String.format("%d Hours %d Minutes", hours, minutes);
        double decimalHours = net.toMinutes() / 60.0;

        return new NetWorkingHours(formatted, decimalHours);
    }

    public NetWorkingHours calculateNetWorkingHoursFromDb(Attendance attendance) {
        if (attendance.clockIn() == null || attendance.clockOut() == null) {
            return new NetWorkingHours("0 Hours 0 Minutes", 0);
        }
        List<BreakRecord> breaks = app.getBreakService().getByAttendanceId(attendance.id());
        List<BreakTime> breakTimes = new ArrayList<>();
        for (BreakRecord br : breaks) {
            breakTimes.add(new BreakTime(
                LocalDateTime.of(attendance.date().toLocalDate(), br.breakStart().toLocalTime()),
                LocalDateTime.of(attendance.date().toLocalDate(), br.breakEnd().toLocalTime())
            ));
        }
        LocalDateTime checkIn = LocalDateTime.of(attendance.date().toLocalDate(), attendance.clockIn().toLocalTime());
        LocalDateTime checkOut = LocalDateTime.of(attendance.date().toLocalDate(), attendance.clockOut().toLocalTime());
        return calculateNetWorkingHours(checkIn, checkOut, breakTimes);
    }

    public double calculateNetWorkingHoursAsDecimal(LocalDateTime checkIn, LocalDateTime checkOut, List<BreakTime> breaks) {
        return calculateNetWorkingHours(checkIn, checkOut, breaks).decimalHours();
    }

    public SalaryResponse calculateSalary(int employeeId, LocalDate startDate, LocalDate endDate, double hourlyRate) {
        Employee emp = app.getEmployeeService().getById(employeeId);
        if (emp == null) {
            throw new IllegalArgumentException("Employee not found: " + employeeId);
        }

        List<Attendance> records = app.getAttendanceService().getByEmployee(employeeId);

        double totalHours = 0;
        double totalNetHours = 0;
        int workDays = 0;
        List<DailyDetail> details = new ArrayList<>();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        for (Attendance a : records) {
            if (a.date() == null) continue;
            LocalDate attDate = a.date().toLocalDate();
            if (attDate.isBefore(startDate) || attDate.isAfter(endDate)) continue;
            if (a.clockIn() == null || a.clockOut() == null) continue;

            long diffMillis = a.clockOut().getTime() - a.clockIn().getTime();
            double grossHours = diffMillis > 0 ? diffMillis / (1000.0 * 60 * 60) : 0;

            NetWorkingHours net = calculateNetWorkingHoursFromDb(a);
            double netHours = net.decimalHours();

            long grossMin = (long)(grossHours * 60);
            long breakMin = grossMin - (long)(netHours * 60);

            details.add(new DailyDetail(
                attDate.format(dateFmt),
                a.clockIn().toLocalTime().format(timeFmt),
                a.clockOut().toLocalTime().format(timeFmt),
                String.format("%d:%02d", grossMin / 60, grossMin % 60),
                String.format("%d:%02d", breakMin / 60, breakMin % 60),
                net.formatted()
            ));

            if (grossHours > 0) {
                totalHours += grossHours;
                totalNetHours += netHours;
                workDays++;
            }
        }

        double totalSalary = totalNetHours * hourlyRate;

        return new SalaryResponse(
            employeeId,
            emp.fullName(),
            startDate.toString(),
            endDate.toString(),
            Math.round(totalHours * 100.0) / 100.0,
            Math.round(totalNetHours * 100.0) / 100.0,
            hourlyRate,
            Math.round(totalSalary * 100.0) / 100.0,
            workDays,
            details
        );
    }
}
