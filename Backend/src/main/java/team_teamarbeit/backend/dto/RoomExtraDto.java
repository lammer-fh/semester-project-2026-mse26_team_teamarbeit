package team_teamarbeit.backend.dto;

import lombok.Builder;
import lombok.Data;
import team_teamarbeit.backend.entity.RoomExtra;

@Data
@Builder
public class RoomExtraDto {
    private String name;
    private String icon;

    public static RoomExtraDto fromRoomExtra(RoomExtra roomExtra) {
        return RoomExtraDto.builder()
            .name(roomExtra.getName())
            .icon(roomExtra.getIcon())
            .build();
    }
}
