package team_teamarbeit.backend.controller;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team_teamarbeit.backend.dto.AvailabilityDto;
import team_teamarbeit.backend.dto.BookingListDto;
import team_teamarbeit.backend.dto.RoomDto;
import team_teamarbeit.backend.dto.RoomListDto;
import team_teamarbeit.backend.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public RoomListDto getRooms(
        @RequestParam(required = false) String filter,
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        return roomService.getRooms(filter, sort, page, size);
    }

    @GetMapping("/{id}")
    public RoomDto getRoom(@PathVariable UUID id) {
        return roomService.getRoom(id);
    }

    @GetMapping("/{id}/availability")
    public AvailabilityDto getAvailability(
        @PathVariable UUID id,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return roomService.getAvailability(id, from, to);
    }

    @GetMapping("/{id}/bookings")
    public BookingListDto getRoomBookings(
        @PathVariable UUID id,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        return roomService.getRoomBookings(id, page, size);
    }
}
