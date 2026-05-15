package team_teamarbeit.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_teamarbeit.backend.dto.RoomReadDto;
import team_teamarbeit.backend.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<RoomReadDto> getAllRooms() {
        return roomRepository.findAll().stream()
            .map(room -> RoomReadDto.builder()
                .roomNumber(room.getRoomNumber())
                .roomTypeName(room.getRoomType().getName())
                .roomTypeDescription(room.getRoomType().getDescription())
                .pricePerNight(room.getRoomType().getPricePerNight())
                .roomExtras(room.getRoomType().getRoomExtras().stream().map(extra -> extra.getName()).toList())
                .build()
            ).toList();
    }
}
