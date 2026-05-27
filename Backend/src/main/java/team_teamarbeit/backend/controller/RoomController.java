package team_teamarbeit.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import team_teamarbeit.backend.dto.PagedResponseDto;
import team_teamarbeit.backend.dto.RoomTypeDto;
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

    public RoomController(RoomService roomService) {
        super();
        this.roomService = roomService;
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
}
