package team_teamarbeit.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomAvailabilityDto {
    private boolean available;

    public static RoomAvailabilityDto fromAvailability(boolean isAvailable) {
        return RoomAvailabilityDto.builder()
            .available(isAvailable)
            .build();
    }
}
