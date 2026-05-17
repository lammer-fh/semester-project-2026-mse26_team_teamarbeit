package team_teamarbeit.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingListDto {
    private List<BookingDto> bookings;
    private PageDto page;
}
