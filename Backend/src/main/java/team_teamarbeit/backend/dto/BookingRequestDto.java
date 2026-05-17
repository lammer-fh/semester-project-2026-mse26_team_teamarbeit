package team_teamarbeit.backend.dto;

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
public class BookingRequestDto {
    private UUID roomId;
    private LocalDate from;
    private LocalDate to;
    private boolean breakfast;
    private UUID guestId;
    private GuestRequestDto guest;
}
