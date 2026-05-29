package team_teamarbeit.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team_teamarbeit.backend.dto.RoomAvailabilityDto;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.entity.RoomType;
import team_teamarbeit.backend.exceptions.ResourceNotFoundException;
import team_teamarbeit.backend.repository.RoomRepository;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityServiceTest {

    @Mock
    private RoomRepository roomRepository; 

    @Mock
    private RoomTypeRepository roomTypeRepository; 

    @InjectMocks
    private RoomAvailabilityService roomAvailabilityService;

    private UUID validRoomTypeId;
    private LocalDate fromDate;
    private LocalDate toDate;

    @BeforeEach
    void setUp() {
        validRoomTypeId = UUID.randomUUID();
        fromDate = LocalDate.of(2026, 6, 1);
        toDate = LocalDate.of(2026, 6, 5);
    }

    @Test
    void checkRoomAvailability_WhenRoomTypeNotFound_ShouldThrowException() {
        when(roomTypeRepository.findById(validRoomTypeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            roomAvailabilityService.checkRoomAvailability(validRoomTypeId, fromDate, toDate)
        );
        
        verify(roomRepository, never()).findAvailableRooms(any(), any(), any());
    }

    @Test
    void checkRoomAvailability_WhenRoomsAvailable_ShouldReturnAvailableTrue() {
        RoomType mockRoomType = new RoomType(); 
        mockRoomType.setId(validRoomTypeId);
        
        Room availableRoom = new Room();

        when(roomTypeRepository.findById(validRoomTypeId)).thenReturn(Optional.of(mockRoomType));
        
        when(roomRepository.findAvailableRooms(validRoomTypeId, fromDate, toDate))
            .thenReturn(List.of(availableRoom));

        RoomAvailabilityDto result = roomAvailabilityService.checkRoomAvailability(validRoomTypeId, fromDate, toDate);

        assertTrue(result.isAvailable());
    }

    @Test
    void checkRoomAvailability_WhenNoRoomsAvailable_ShouldReturnAvailableFalse() {
        RoomType mockRoomType = new RoomType(); 
        mockRoomType.setId(validRoomTypeId);

        when(roomTypeRepository.findById(validRoomTypeId)).thenReturn(Optional.of(mockRoomType));
        
        when(roomRepository.findAvailableRooms(validRoomTypeId, fromDate, toDate))
            .thenReturn(Collections.emptyList());

        RoomAvailabilityDto result = roomAvailabilityService.checkRoomAvailability(validRoomTypeId, fromDate, toDate);

        assertFalse(result.isAvailable());
    }
}