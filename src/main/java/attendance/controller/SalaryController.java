package attendance.controller;

import attendance.dto.SalaryResponse;
import attendance.service.SalaryService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private final SalaryService salaryService = new SalaryService();

    @GetMapping
    public ResponseEntity<SalaryResponse> calculateSalary(
            @RequestParam int employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "1000") double hourlyRate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().build();
        }

        if (hourlyRate <= 0) hourlyRate = 1000;

        try {
            SalaryResponse response = salaryService.calculateSalary(employeeId, startDate, endDate, hourlyRate);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
