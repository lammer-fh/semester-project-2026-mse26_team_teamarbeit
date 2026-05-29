package team_teamarbeit.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import team_teamarbeit.backend.dto.DateRangeQueryDto;
import team_teamarbeit.backend.dto.PagedResponseDto;
import team_teamarbeit.backend.dto.RoomAvailabilityDto;
import team_teamarbeit.backend.dto.RoomTypeDto;
import team_teamarbeit.backend.service.RoomAvailabilityService;
import team_teamarbeit.backend.service.RoomService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final RoomAvailabilityService roomAvailabilityService;

    public RoomController(RoomService roomService, RoomAvailabilityService roomAvailabilityService) {
        super();
        this.roomService = roomService;
        this.roomAvailabilityService = roomAvailabilityService; 
    }

    @GetMapping
    public PagedResponseDto<RoomTypeDto> getRooms(
            @RequestParam(required = false, defaultValue = "") 
            String filter,
            @PageableDefault(size = 5, page = 0, sort = {}) 
            Pageable pageable
        ) {
        return roomService.getRooms(filter, pageable);
    }
    
    @GetMapping("/{id}")
    public RoomTypeDto getRoomDetails(@PathVariable UUID id) {
        return roomService.getRoomDetails(id);
    }

    @Operation(summary = "Check room availability for a given date range")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Room availability retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid date range provided"),
        @ApiResponse(responseCode = "404", description = "Room type not found")
    })
    @GetMapping("/{id}/availability")
    public RoomAvailabilityDto checkRoomAvailability(
        @PathVariable 
        UUID id,
        @Valid
        DateRangeQueryDto dateRange
    ) {
        return roomAvailabilityService.checkRoomAvailability(id, dateRange.getFrom(), dateRange.getTo());
    }
}
