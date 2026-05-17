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
import team_teamarbeit.backend.dto.GuestDto;
import team_teamarbeit.backend.dto.GuestListDto;
import team_teamarbeit.backend.dto.GuestRequestDto;
import team_teamarbeit.backend.dto.PageDto;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Guest;
import team_teamarbeit.backend.exception.ApiException;
import team_teamarbeit.backend.repository.BookingRepository;
import team_teamarbeit.backend.repository.GuestRepository;

@Service
@Transactional(readOnly = true)
public class GuestService {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    public GuestService(GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    public GuestListDto getGuests(Integer page, Integer size) {
        if (page == null && size == null) {
            List<Guest> guests = guestRepository.findAll();
            return new GuestListDto(guests.stream().map(this::toGuestDto).toList(), toPageDto(guests.size()));
        }

        Page<Guest> guests = guestRepository.findAll(PageRequest.of(toPageIndex(page), size == null ? DEFAULT_SIZE : size));
        return new GuestListDto(guests.map(this::toGuestDto).toList(), toPageDto(guests));
    }

    public GuestDto getGuest(UUID id) {
        return toGuestDto(findGuest(id));
    }

    @Transactional
    public GuestDto createGuest(GuestRequestDto request) {
        validateGuestRequest(request, null);
        Guest guest = Guest.builder()
            .mail(request.getEmail())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .build();
        return toGuestDto(guestRepository.save(guest));
    }

    @Transactional
    public GuestDto updateGuest(UUID id, GuestRequestDto request) {
        Guest guest = findGuest(id);
        validateGuestRequest(request, id);
        guest.setMail(request.getEmail());
        guest.setFirstName(request.getFirstName());
        guest.setLastName(request.getLastName());
        return toGuestDto(guestRepository.save(guest));
    }

    @Transactional
    public void deleteGuest(UUID id) {
        if (!guestRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "The requested guest id does not exist.");
        }
        bookingRepository.deleteByGuest_Id(id);
        guestRepository.deleteById(id);
    }

    public BookingListDto getGuestBookings(UUID guestId, Integer page, Integer size) {
        findGuest(guestId);

        if (page == null && size == null) {
            List<Booking> bookings = bookingRepository.findByGuest_Id(guestId);
            return new BookingListDto(bookings.stream().map(this::toBookingDto).toList(), toPageDto(bookings.size()));
        }

        Page<Booking> bookings = bookingRepository.findByGuest_Id(guestId, PageRequest.of(toPageIndex(page), size == null ? DEFAULT_SIZE : size));
        return new BookingListDto(bookings.map(this::toBookingDto).toList(), toPageDto(bookings));
    }

    private Guest findGuest(UUID id) {
        return guestRepository.findById(id)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No guest with the given id found."));
    }

    private void validateGuestRequest(GuestRequestDto request, UUID existingId) {
        if (request == null || isBlank(request.getEmail()) || isBlank(request.getFirstName()) || isBlank(request.getLastName()) || !isValidEmail(request.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The sent request body was malformed.");
        }

        boolean mailExists = existingId == null
            ? guestRepository.existsByMailIgnoreCase(request.getEmail())
            : guestRepository.existsByMailIgnoreCaseAndIdNot(request.getEmail(), existingId);
        if (mailExists) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email is already registered.");
        }
    }

    private GuestDto toGuestDto(Guest guest) {
        return new GuestDto(guest.getId(), guest.getMail(), guest.getFirstName(), guest.getLastName());
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
