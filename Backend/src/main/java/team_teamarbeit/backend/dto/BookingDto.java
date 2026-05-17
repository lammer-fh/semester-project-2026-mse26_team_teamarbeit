package team_teamarbeit.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private UUID id;
    private UUID guestId;
    private UUID roomId;
    private LocalDate from;
    private LocalDate to;
    private boolean breakfast;
    private long duration;
    private BigDecimal totalPrice;
}
