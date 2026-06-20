package attendance.controller;

import attendance.dao.model.BreakRecord;
import attendance.dto.BreakTime;
import attendance.dto.NetWorkingHours;
import attendance.service.App;
import attendance.service.SalaryService;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/breaks")
public class BreakController {

    private final App app = App.getSingleton();
    private final SalaryService salaryService = new SalaryService();

    @GetMapping("/{attendanceId}")
    public ResponseEntity<List<BreakRecord>> getBreaks(@PathVariable int attendanceId) {
        return ResponseEntity.ok(app.getBreakService().getByAttendanceId(attendanceId));
    }

    @PostMapping("/{attendanceId}")
    public ResponseEntity<String> addBreak(
            @PathVariable int attendanceId,
            @RequestParam String breakStart,
            @RequestParam String breakEnd) {
        try {
            Time start = Time.valueOf(LocalTime.parse(breakStart));
            Time end = Time.valueOf(LocalTime.parse(breakEnd));
            app.getBreakService().create(attendanceId, start, end);
            return ResponseEntity.ok("Break added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid time format (use HH:mm)");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBreak(@PathVariable int id) {
        app.getBreakService().deleteById(id);
        return ResponseEntity.ok("Break deleted");
    }

    @GetMapping("/net-hours/{attendanceId}")
    public ResponseEntity<NetWorkingHours> getNetHours(@PathVariable int attendanceId) {
        var att = app.getAttendanceService().getById(attendanceId);
        if (att == null || att.clockIn() == null || att.clockOut() == null) {
            return ResponseEntity.badRequest().build();
        }
        var br = app.getBreakService().getByAttendanceId(attendanceId);
        List<BreakTime> breaks = new ArrayList<>();
        for (BreakRecord b : br) {
            breaks.add(new BreakTime(
                LocalDateTime.of(att.date().toLocalDate(), b.breakStart().toLocalTime()),
                LocalDateTime.of(att.date().toLocalDate(), b.breakEnd().toLocalTime())
            ));
        }
        LocalDateTime checkIn = LocalDateTime.of(att.date().toLocalDate(), att.clockIn().toLocalTime());
        LocalDateTime checkOut = LocalDateTime.of(att.date().toLocalDate(), att.clockOut().toLocalTime());
        return ResponseEntity.ok(salaryService.calculateNetWorkingHours(checkIn, checkOut, breaks));
    }
}
