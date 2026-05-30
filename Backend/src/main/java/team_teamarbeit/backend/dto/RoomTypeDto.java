package team_teamarbeit.backend.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import team_teamarbeit.backend.entity.RoomImage;
import team_teamarbeit.backend.entity.RoomType;

@Data
@Builder
public class RoomTypeDto {
    private UUID id;
    private String name;
    private String description;
    private Double pricePerNight;
    private String coverImagePath;
    private List<String> imagePaths;
    private List<RoomExtraDto> roomExtras;

    public static RoomTypeDto fromRoomType(RoomType roomType) {
        return RoomTypeDto.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .description(roomType.getDescription())
                .pricePerNight(roomType.getPricePerNight().doubleValue())
                .coverImagePath(roomType.getCoverImagePath())
                .imagePaths(roomType.getRoomImages().stream().map(RoomImage::getImagePath).toList())
                .roomExtras(roomType.getRoomExtras().stream().map(RoomExtraDto::fromRoomExtra).toList())
                .build();
    }
}