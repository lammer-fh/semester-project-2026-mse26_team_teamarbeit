package team_teamarbeit.backend.service;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import team_teamarbeit.backend.dto.RoomAvailabilityDto;
import team_teamarbeit.backend.exceptions.ResourceNotFoundException;
import team_teamarbeit.backend.repository.RoomRepository;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@Service
public class RoomAvailabilityService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    public RoomAvailabilityService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository) {
        super();
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    public RoomAvailabilityDto checkRoomAvailability(UUID roomTypeId, LocalDate from, LocalDate to) {
        // check if room type exists
        roomTypeRepository.findById(roomTypeId).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));

        // check if any room of that type is available for the given time range
        boolean isAvailable = !roomRepository.findAvailableRooms(roomTypeId, from, to).isEmpty();
        return RoomAvailabilityDto.fromAvailability(isAvailable);
    }
}
