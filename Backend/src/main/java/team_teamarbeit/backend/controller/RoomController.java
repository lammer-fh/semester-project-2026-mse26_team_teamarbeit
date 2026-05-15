package team_teamarbeit.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import team_teamarbeit.backend.dto.RoomReadDto;
import team_teamarbeit.backend.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomReadDto> getRooms() {
        return roomService.getAllRooms();
    }
    
}
