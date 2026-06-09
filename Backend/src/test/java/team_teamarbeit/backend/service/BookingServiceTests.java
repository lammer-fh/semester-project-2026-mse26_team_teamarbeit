package team_teamarbeit.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team_teamarbeit.backend.dto.BookRoomRequestDto;
import team_teamarbeit.backend.dto.BookRoomRequestDto.GuestRequestDto;
import team_teamarbeit.backend.dto.BookingDto;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Guest;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.entity.RoomType;
import team_teamarbeit.backend.enums.BookingStatusType;
import team_teamarbeit.backend.exceptions.BadRequestException;
import team_teamarbeit.backend.exceptions.BookingConflictException;
import team_teamarbeit.backend.repository.BookingRepository;
import team_teamarbeit.backend.repository.GuestRepository;
import team_teamarbeit.backend.repository.RoomRepository;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceTests {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private GuestRepository guestRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomTypeRepository roomTypeRepository;

    private BookingService bookingService;
    private UUID roomTypeId;
    private Room room;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(
            bookingRepository, guestRepository, roomRepository, roomTypeRepository);
        roomTypeId = UUID.randomUUID();
        RoomType roomType = RoomType.builder()
            .id(roomTypeId)
            .pricePerNight(BigDecimal.valueOf(100))
            .build();
        room = Room.builder()
            .id(UUID.randomUUID())
            .roomType(roomType)
            .build();
    }

    @Test
    void bookRoomWithNewGuestCreatesBookingAndCalculatesTotalPrice() {
        BookRoomRequestDto request = requestWithGuest();
        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(true);
        when(roomRepository.findAvailableRoomsForUpdate(roomTypeId, request.getFrom(), request.getTo()))
            .thenReturn(List.of(room));
        when(guestRepository.findByMailIgnoreCase("guest@example.com")).thenReturn(Optional.empty());
        when(guestRepository.save(any(Guest.class))).thenAnswer(invocation -> {
            Guest guest = invocation.getArgument(0);
            guest.setId(UUID.randomUUID());
            return guest;
        });
        when(bookingRepository.existsByReferenceKey(any())).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId(UUID.randomUUID());
            return booking;
        });

        BookingDto result = bookingService.bookRoom(request);

        assertEquals(4, result.getDuration());
        assertEquals(460.0, result.getTotalPrice());
        assertEquals(roomTypeId, result.getRoomId());

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        assertEquals(BookingStatusType.CONFIRMED, bookingCaptor.getValue().getStatus());
        assertEquals(new BigDecimal("460"), bookingCaptor.getValue().getTotalPrice());
    }

    @Test
    void bookRoomWithKnownEmailReusesExistingGuest() {
        BookRoomRequestDto request = requestWithGuest();
        Guest existingGuest = Guest.builder()
            .id(UUID.randomUUID())
            .mail("guest@example.com")
            .build();
        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(true);
        when(roomRepository.findAvailableRoomsForUpdate(roomTypeId, request.getFrom(), request.getTo()))
            .thenReturn(List.of(room));
        when(guestRepository.findByMailIgnoreCase("guest@example.com")).thenReturn(Optional.of(existingGuest));
        when(bookingRepository.existsByReferenceKey(any())).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId(UUID.randomUUID());
            return booking;
        });

        BookingDto result = bookingService.bookRoom(request);

        assertEquals(existingGuest.getId(), result.getUserId());
        verify(guestRepository, never()).save(any());
    }

    @Test
    void bookRoomWithInvalidRoomTypeThrowsBadRequest() {
        BookRoomRequestDto request = requestWithGuest();
        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> bookingService.bookRoom(request));
        verify(roomRepository, never()).findAvailableRoomsForUpdate(any(), any(), any());
    }

    @Test
    void bookRoomWithoutAvailabilityThrowsConflict() {
        BookRoomRequestDto request = requestWithGuest();
        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(true);
        when(roomRepository.findAvailableRoomsForUpdate(roomTypeId, request.getFrom(), request.getTo()))
            .thenReturn(List.of());

        assertThrows(BookingConflictException.class, () -> bookingService.bookRoom(request));
        verify(bookingRepository, never()).save(any());
    }

    private BookRoomRequestDto requestWithGuest() {
        GuestRequestDto guest = new GuestRequestDto();
        guest.setFirstName("Test");
        guest.setLastName("Guest");
        guest.setEmail("Guest@Example.com");

        BookRoomRequestDto request = new BookRoomRequestDto();
        request.setRoomId(roomTypeId);
        request.setFrom(LocalDate.now().plusDays(1));
        request.setTo(LocalDate.now().plusDays(5));
        request.setBreakfast(true);
        request.setGuest(guest);
        return request;
    }
}
