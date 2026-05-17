package team_teamarbeit.backend.controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_teamarbeit.backend.dto.BookingListDto;
import team_teamarbeit.backend.dto.GuestDto;
import team_teamarbeit.backend.dto.GuestListDto;
import team_teamarbeit.backend.dto.GuestRequestDto;
import team_teamarbeit.backend.service.GuestService;

@RestController
@RequestMapping("/guests")
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public GuestListDto getGuests(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        return guestService.getGuests(page, size);
    }

    @GetMapping("/{id}")
    public GuestDto getGuest(@PathVariable UUID id) {
        return guestService.getGuest(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDto createGuest(@RequestBody GuestRequestDto request) {
        return guestService.createGuest(request);
    }

    @PutMapping("/{id}")
    public GuestDto updateGuest(@PathVariable UUID id, @RequestBody GuestRequestDto request) {
        return guestService.updateGuest(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable UUID id) {
        guestService.deleteGuest(id);
    }

    @GetMapping("/{id}/bookings")
    public BookingListDto getGuestBookings(
        @PathVariable UUID id,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        return guestService.getGuestBookings(id, page, size);
    }
}
