package team_teamarbeit.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_teamarbeit.backend.dto.BookingDto;
import team_teamarbeit.backend.dto.BookingListDto;
import team_teamarbeit.backend.dto.BookingRequestDto;
import team_teamarbeit.backend.dto.GuestRequestDto;
import team_teamarbeit.backend.dto.PageDto;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Guest;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.enums.BookingStatusType;
import team_teamarbeit.backend.exception.ApiException;
import team_teamarbeit.backend.repository.BookingRepository;
import team_teamarbeit.backend.repository.GuestRepository;
import team_teamarbeit.backend.repository.RoomRepository;

@Service
public class BookingService {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, GuestRepository guestRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
    }

    @Transactional(readOnly = true)
    public BookingListDto getBookings(Integer page, Integer size) {
        if (page == null && size == null) {
            List<Booking> bookings = bookingRepository.findAll();
            return new BookingListDto(bookings.stream().map(this::toBookingDto).toList(), toPageDto(bookings.size()));
        }

        Page<Booking> bookings = bookingRepository.findAll(PageRequest.of(toPageIndex(page), size == null ? DEFAULT_SIZE : size));
        return new BookingListDto(bookings.map(this::toBookingDto).toList(), toPageDto(bookings));
    }

    @Transactional
    public BookingDto createBooking(BookingRequestDto request) {
        validateRequest(request);
        Room room = findRoom(request.getRoomId());
        Guest guest = resolveGuest(request);

        if (hasConflictingBooking(room.getId(), request.getFrom(), request.getTo())) {
            throw new ApiException(HttpStatus.CONFLICT, "The requested room is no longer available for the selected time range.");
        }

        Booking booking = Booking.builder()
            .arrivalDate(request.getFrom())
            .departureDate(request.getTo())
            .breakfast(request.isBreakfast())
            .status(BookingStatusType.CONFIRMED)
            .totalPrice(calculateTotalPrice(room, request.getFrom(), request.getTo()))
            .room(room)
            .guest(guest)
            .build();

        return toBookingDto(bookingRepository.save(booking));
    }

    @Transactional
    public BookingDto updateBooking(UUID id, BookingRequestDto request) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "The requested booking id does not exist."));
        validateRequest(request);
        Room room = findRoom(request.getRoomId());
        Guest guest = resolveGuest(request);

        if (bookingRepository.existsByRoom_IdAndIdNotAndStatusNotAndArrivalDateLessThanAndDepartureDateGreaterThan(
            room.getId(),
            id,
            BookingStatusType.CANCELLED,
            request.getTo(),
            request.getFrom()
        )) {
            throw new ApiException(HttpStatus.CONFLICT, "The requested room is not available for the selected time range.");
        }

        booking.setArrivalDate(request.getFrom());
        booking.setDepartureDate(request.getTo());
        booking.setBreakfast(request.isBreakfast());
        booking.setStatus(BookingStatusType.CONFIRMED);
        booking.setTotalPrice(calculateTotalPrice(room, request.getFrom(), request.getTo()));
        booking.setRoom(room);
        booking.setGuest(guest);

        return toBookingDto(bookingRepository.save(booking));
    }

    @Transactional
    public void deleteBooking(UUID id) {
        if (!bookingRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "The requested booking id does not exist.");
        }
        bookingRepository.deleteById(id);
    }

    private void validateRequest(BookingRequestDto request) {
        if (request == null || request.getRoomId() == null || (request.getGuestId() == null && request.getGuest() == null)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The sent request body was malformed.");
        }

        if (request.getFrom() == null || request.getTo() == null || request.getFrom().isBefore(LocalDate.now()) || !request.getTo().isAfter(request.getFrom())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The selected date range is invalid.");
        }
    }

    private Room findRoom(UUID roomId) {
        return roomRepository.findById(roomId)
            .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "The requested room id is invalid."));
    }

    private Guest resolveGuest(BookingRequestDto request) {
        if (request.getGuest() != null) {
            return upsertGuest(request.getGuest());
        }

        return guestRepository.findById(request.getGuestId())
            .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "The requested guest id is invalid."));
    }

    private Guest upsertGuest(GuestRequestDto request) {
        if (isBlank(request.getEmail()) || isBlank(request.getFirstName()) || isBlank(request.getLastName()) || !isValidEmail(request.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The guest data is invalid.");
        }

        return guestRepository.findByMailIgnoreCase(request.getEmail())
            .orElseGet(() -> guestRepository.save(Guest.builder()
                .mail(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build()));
    }

    private boolean hasConflictingBooking(UUID roomId, LocalDate from, LocalDate to) {
        return bookingRepository.existsByRoom_IdAndStatusNotAndArrivalDateLessThanAndDepartureDateGreaterThan(
            roomId,
            BookingStatusType.CANCELLED,
            to,
            from
        );
    }

    private BigDecimal calculateTotalPrice(Room room, LocalDate from, LocalDate to) {
        return room.getRoomType().getPricePerNight().multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(from, to)));
    }

    private BookingDto toBookingDto(Booking booking) {
        LocalDate from = booking.getArrivalDate();
        LocalDate to = booking.getDepartureDate();
        return new BookingDto(
            booking.getId(),
            booking.getGuest().getId(),
            booking.getRoom().getId(),
            from,
            to,
            booking.isBreakfast(),
            ChronoUnit.DAYS.between(from, to),
            booking.getTotalPrice() == null ? BigDecimal.ZERO : booking.getTotalPrice()
        );
    }

    private boolean isValidEmail(String value) {
        return value != null && value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private int toPageIndex(Integer page) {
        return Math.max((page == null ? DEFAULT_PAGE : page) - 1, 0);
    }

    private PageDto toPageDto(Page<?> page) {
        return new PageDto(page.getSize(), page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1);
    }

    private PageDto toPageDto(int totalElements) {
        return new PageDto(totalElements, totalElements, totalElements == 0 ? 0 : 1, totalElements == 0 ? 0 : 1);
    }
}
