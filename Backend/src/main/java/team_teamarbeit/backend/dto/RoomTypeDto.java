package team_teamarbeit.backend.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import team_teamarbeit.backend.entity.RoomType;

@Data
@Builder
public class RoomTypeDto {
    UUID id;
    String name;
    String description;
    Double pricePerNight;
    List<String> imagePaths;
    List<RoomExtraDto> roomExtras;

    public static RoomTypeDto fromRoomType(RoomType roomType) {
        return RoomTypeDto.builder()
            .id(roomType.getId())
            .name(roomType.getName())
            .description(roomType.getDescription())
            .pricePerNight(roomType.getPricePerNight().doubleValue())
            .imagePaths(roomType.getRoomImages().stream().map(image -> image.getImagePath()).toList())
            .roomExtras(roomType.getRoomExtras().stream().map(RoomExtraDto::fromRoomExtra).toList())
            .build();
    }
}
