package team_teamarbeit.backend.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_teamarbeit.backend.dto.BookRoomRequestDto;
import team_teamarbeit.backend.dto.BookRoomRequestDto.GuestRequestDto;
import team_teamarbeit.backend.dto.BookingDto;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Guest;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.enums.BookingStatusType;
import team_teamarbeit.backend.exceptions.BadRequestException;
import team_teamarbeit.backend.exceptions.BookingConflictException;
import team_teamarbeit.backend.repository.BookingRepository;
import team_teamarbeit.backend.repository.GuestRepository;
import team_teamarbeit.backend.repository.RoomRepository;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@Service
public class BookingService {
    private static final BigDecimal BREAKFAST_PRICE_PER_DAY = BigDecimal.valueOf(15);

    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    public BookingService(
            BookingRepository bookingRepository,
            GuestRepository guestRepository,
            RoomRepository roomRepository,
            RoomTypeRepository roomTypeRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Transactional
    public BookingDto bookRoom(BookRoomRequestDto request) {
        if (!roomTypeRepository.existsById(request.getRoomId())) {
            throw new BadRequestException("Invalid roomId");
        }

        List<Room> availableRooms = roomRepository.findAvailableRoomsForUpdate(
            request.getRoomId(), request.getFrom(), request.getTo());
        if (availableRooms.isEmpty()) {
            throw new BookingConflictException(
                "The requested room is not available for the selected time range");
        }

        Room room = availableRooms.getFirst();
        Guest guest = resolveGuest(request);
        long duration = ChronoUnit.DAYS.between(request.getFrom(), request.getTo());
        BigDecimal totalPrice = room.getRoomType().getPricePerNight().multiply(BigDecimal.valueOf(duration));
        if (request.isBreakfast()) {
            totalPrice = totalPrice.add(BREAKFAST_PRICE_PER_DAY.multiply(BigDecimal.valueOf(duration)));
        }

        Booking booking = Booking.builder()
            .referenceKey(generateReferenceKey())
            .arrivalDate(request.getFrom())
            .departureDate(request.getTo())
            .breakfast(request.isBreakfast())
            .status(BookingStatusType.CONFIRMED)
            .totalPrice(totalPrice)
            .guest(guest)
            .room(room)
            .build();

        return BookingDto.fromBooking(bookingRepository.save(booking));
    }

    private Guest resolveGuest(BookRoomRequestDto request) {
        if (request.getGuest() != null) {
            GuestRequestDto guestRequest = request.getGuest();
            String email = guestRequest.getEmail().trim().toLowerCase(Locale.ROOT);
            return guestRepository.findByMailIgnoreCase(email)
                .orElseGet(() -> guestRepository.save(Guest.builder()
                    .mail(email)
                    .firstName(guestRequest.getFirstName().trim())
                    .lastName(guestRequest.getLastName().trim())
                    .build()));
        }

        return guestRepository.findById(request.getUserId())
            .orElseThrow(() -> new BadRequestException("Invalid userId"));
    }

    private String generateReferenceKey() {
        String referenceKey;
        do {
            referenceKey = "BHT" + ThreadLocalRandom.current().nextInt(100000, 1000000);
        } while (bookingRepository.existsByReferenceKey(referenceKey));
        return referenceKey;
    }
}
