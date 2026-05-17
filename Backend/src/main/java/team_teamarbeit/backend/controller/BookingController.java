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
import team_teamarbeit.backend.dto.BookingDto;
import team_teamarbeit.backend.dto.BookingListDto;
import team_teamarbeit.backend.dto.BookingRequestDto;
import team_teamarbeit.backend.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public BookingListDto getBookings(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        return bookingService.getBookings(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestBody BookingRequestDto request) {
        return bookingService.createBooking(request);
    }

    @PutMapping("/{id}")
    public BookingDto updateBooking(@PathVariable UUID id, @RequestBody BookingRequestDto request) {
        return bookingService.updateBooking(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
    }
}
