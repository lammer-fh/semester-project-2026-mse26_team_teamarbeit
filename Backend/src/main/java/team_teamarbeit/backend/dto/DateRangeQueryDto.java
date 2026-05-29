package team_teamarbeit.backend.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DateRangeQueryDto {
    @NotNull(message = "The 'from' date must not be null.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;

    @NotNull(message = "The 'to' date must not be null.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;

    @AssertTrue(message = "The 'from' date must be before the 'to' date.")
    public boolean isDateRangeValid() {
        if (from == null || to == null) {
            return true; // Let @NotNull handle this case
        }
        return from.isBefore(to);
    }

    @AssertTrue(message = "The 'from' date must not be in the past.")
    public boolean isFromDateNotInPast() {
        if (from == null) {
            return true; // Let @NotNull handle this case
        }
        // today is a valid arrival date
        return from.isAfter(LocalDate.now().minusDays(1));
    }

    @AssertTrue(message = "The 'to' date must not be in the past.")
    public boolean isToDateNotInPast() {
        if (to == null) {
            return true; // Let @NotNull handle this case
        }
        return to.isAfter(LocalDate.now());
    }
}
