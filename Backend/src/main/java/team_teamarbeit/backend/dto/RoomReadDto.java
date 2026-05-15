package team_teamarbeit.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomReadDto {
    private String roomNumber;
    private String roomTypeName;
    private String roomTypeDescription;
    private Double pricePerNight;
    private List<String> roomExtras;
}
