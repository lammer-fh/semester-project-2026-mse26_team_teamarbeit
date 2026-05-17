package team_teamarbeit.backend.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private UUID id;
    private String name;
    private String description;
    private double pricePerNight;
    private List<String> extras;
    private List<String> imagePaths;
}
