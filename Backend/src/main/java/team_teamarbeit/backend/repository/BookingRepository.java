package team_teamarbeit.backend.repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.enums.BookingStatusType;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByRoom_IdAndStatusNotAndArrivalDateLessThanAndDepartureDateGreaterThan(
        UUID roomId,
        BookingStatusType status,
        LocalDate departureDate,
        LocalDate arrivalDate
    );

    boolean existsByRoom_IdAndIdNotAndStatusNotAndArrivalDateLessThanAndDepartureDateGreaterThan(
        UUID roomId,
        UUID bookingId,
        BookingStatusType status,
        LocalDate departureDate,
        LocalDate arrivalDate
    );

    Page<Booking> findByRoom_Id(UUID roomId, Pageable pageable);

    List<Booking> findByRoom_Id(UUID roomId);

    Page<Booking> findByGuest_Id(UUID guestId, Pageable pageable);

    List<Booking> findByGuest_Id(UUID guestId);

    void deleteByGuest_Id(UUID guestId);
}
