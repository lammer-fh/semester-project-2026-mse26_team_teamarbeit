package team_teamarbeit.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query("SELECT r FROM Rooms r WHERE r.roomType.id = :roomTypeId AND r.id NOT IN " +
        "(SELECT b.room.id FROM Bookings b WHERE b.arrivalDate < :to AND b.departureDate > :from)")
    List<Room> findAvailableRooms(
        @Param("roomTypeId") UUID roomTypeId, 
        @Param("from") LocalDate from, 
        @Param("to") LocalDate to
    );
}
