package team_teamarbeit.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_teamarbeit.backend.dto.AvailabilityDto;
import team_teamarbeit.backend.dto.BookingDto;
import team_teamarbeit.backend.dto.BookingListDto;
import team_teamarbeit.backend.dto.PageDto;
import team_teamarbeit.backend.dto.RoomDto;
import team_teamarbeit.backend.dto.RoomListDto;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.entity.RoomExtra;
import team_teamarbeit.backend.entity.RoomImage;
import team_teamarbeit.backend.enums.BookingStatusType;
import team_teamarbeit.backend.exception.ApiException;
import team_teamarbeit.backend.repository.BookingRepository;
import team_teamarbeit.backend.repository.RoomRepository;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public RoomListDto getRooms(String filter, String sort, Integer page, Integer size) {
        String normalizedFilter = filter == null ? "" : filter;
        PageRequest pageRequest = PageRequest.of(toPageIndex(page), size == null ? DEFAULT_SIZE : size, toSort(sort));
        Page<Room> rooms = normalizedFilter.isBlank()
            ? roomRepository.findAll(pageRequest)
            : roomRepository.findByRoomType_NameContainingIgnoreCase(normalizedFilter, pageRequest);

        return new RoomListDto(rooms.map(this::toRoomDto).toList(), toPageDto(rooms));
    }

    public RoomDto getRoom(UUID id) {
        return toRoomDto(findRoom(id));
    }

    public AvailabilityDto getAvailability(UUID roomId, LocalDate from, LocalDate to) {
        findRoom(roomId);
        validateDateRange(from, to);
        return new AvailabilityDto(!hasConflictingBooking(roomId, from, to));
    }

    public BookingListDto getRoomBookings(UUID roomId, Integer page, Integer size) {
        findRoom(roomId);

        if (page == null && size == null) {
            List<Booking> bookings = bookingRepository.findByRoom_Id(roomId);
            return new BookingListDto(bookings.stream().map(this::toBookingDto).toList(), toPageDto(bookings.size()));
        }

        Page<Booking> bookings = bookingRepository.findByRoom_Id(roomId, PageRequest.of(toPageIndex(page), size == null ? DEFAULT_SIZE : size));
        return new BookingListDto(bookings.map(this::toBookingDto).toList(), toPageDto(bookings));
    }

    private Room findRoom(UUID id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No room with the given id found."));
    }

    private boolean hasConflictingBooking(UUID roomId, LocalDate from, LocalDate to) {
        return bookingRepository.existsByRoom_IdAndStatusNotAndArrivalDateLessThanAndDepartureDateGreaterThan(
            roomId,
            BookingStatusType.CANCELLED,
            to,
            from
        );
    }

    private void validateDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isBefore(LocalDate.now()) || !to.isAfter(from)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The selected date range is invalid.");
        }
    }

    private RoomDto toRoomDto(Room room) {
        return new RoomDto(
            room.getId(),
            room.getRoomType().getName(),
            room.getRoomType().getDescription(),
            room.getRoomType().getPricePerNight().doubleValue(),
            room.getRoomType().getRoomExtras().stream().map(RoomExtra::getName).sorted().toList(),
            room.getRoomType().getRoomImages().stream().map(RoomImage::getImagePath).sorted().toList()
        );
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

    private Sort toSort(String sort) {
        return switch (sort == null ? "" : sort) {
            case "name" -> Sort.by("roomType.name");
            case "description" -> Sort.by("roomType.description");
            case "pricePerNight" -> Sort.by("roomType.pricePerNight");
            default -> Sort.by("roomType.name");
        };
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
