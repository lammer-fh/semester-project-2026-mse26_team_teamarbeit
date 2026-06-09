package team_teamarbeit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_teamarbeit.backend.dto.BookRoomRequestDto;
import team_teamarbeit.backend.dto.BookingDto;
import team_teamarbeit.backend.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Book a room for a selected date range")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Booking created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid booking request"),
        @ApiResponse(responseCode = "409", description = "No room is available for the selected date range")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto bookRoom(@Valid @RequestBody BookRoomRequestDto request) {
        return bookingService.bookRoom(request);
    }
}
