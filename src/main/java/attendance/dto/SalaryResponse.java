package attendance.dto;

import java.util.List;

public record SalaryResponse(
    int employeeId,
    String employeeName,
    String startDate,
    String endDate,
    double totalHours,
    double totalNetHours,
    double hourlyRate,
    double totalSalary,
    int workDays,
    List<DailyDetail> dailyDetails
) {

    public record DailyDetail(String date, String clockIn, String clockOut, String grossHours, String breakHours, String netHours) {}
}
