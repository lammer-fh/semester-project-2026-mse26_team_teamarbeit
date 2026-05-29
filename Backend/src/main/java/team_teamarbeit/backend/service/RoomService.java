package team_teamarbeit.backend.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team_teamarbeit.backend.dto.PagedResponseDto;
import team_teamarbeit.backend.dto.RoomTypeDto;
import team_teamarbeit.backend.entity.RoomType;
import team_teamarbeit.backend.exceptions.ResourceNotFoundException;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@Service
public class RoomService {
    private final RoomTypeRepository roomTypeRepo;

    public RoomService(RoomTypeRepository roomTypeRepository) {
        super();
        this.roomTypeRepo = roomTypeRepository;
    }

    public PagedResponseDto<RoomTypeDto> getRooms(String filter, Pageable pageable) {
        Page<RoomType> roomTypePage = roomTypeRepo.findByNameContainingIgnoreCase(filter, pageable);
        return PagedResponseDto.fromPage(roomTypePage, RoomTypeDto::fromRoomType);
    }

    public RoomTypeDto getRoomDetails(UUID roomTypeId) {
        RoomType roomType = roomTypeRepo.findById(roomTypeId).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        return RoomTypeDto.fromRoomType(roomType);
    }
}
