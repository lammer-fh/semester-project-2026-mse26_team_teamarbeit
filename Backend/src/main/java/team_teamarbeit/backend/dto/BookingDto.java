package team_teamarbeit.backend.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import team_teamarbeit.backend.entity.Booking;

@Data
@Builder
public class BookingDto {
    private UUID id;
    private UUID userId;
    private UUID roomId;
    private LocalDate from;
    private LocalDate to;
    private boolean breakfast;
    private long duration;
    private Double totalPrice;

    public static BookingDto fromBooking(Booking booking) {
        return BookingDto.builder()
            .id(booking.getId())
            .userId(booking.getGuest().getId())
            .roomId(booking.getRoom().getRoomType().getId())
            .from(booking.getArrivalDate())
            .to(booking.getDepartureDate())
            .breakfast(booking.isBreakfast())
            .duration(ChronoUnit.DAYS.between(booking.getArrivalDate(), booking.getDepartureDate()))
            .totalPrice(booking.getTotalPrice().doubleValue())
            .build();
    }
}
