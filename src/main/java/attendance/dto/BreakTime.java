package attendance.dto;

import java.time.LocalDateTime;

public record BreakTime(LocalDateTime breakStart, LocalDateTime breakEnd) {}
