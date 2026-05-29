package team_teamarbeit.backend.repository;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.entity.RoomType;

@DataJpaTest
class RoomRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    private RoomType standardRoomType;
    private Room room101;
    private Room room102;

    @BeforeEach
    void setUp() {
        // Create a RoomType
        standardRoomType = new RoomType();
        standardRoomType.setName("Standard");
        entityManager.persist(standardRoomType);

        // Create two rooms of this type
        room101 = new Room();
        room101.setRoomNumber("101");
        room101.setRoomType(standardRoomType);
        entityManager.persist(room101);

        room102 = new Room();
        room102.setRoomNumber("102");
        room102.setRoomType(standardRoomType);
        entityManager.persist(room102);
    }

    @Test
    void findAvailableRooms_WhenNoBookingExist_ShouldReturnAllOfThatType() {
        // arrange
        LocalDate from = LocalDate.of(2026, 6, 1);
        LocalDate to = LocalDate.of(2026, 6, 5);
        
        // act
        List<Room> availableRooms = roomRepository.findAvailableRooms(standardRoomType.getId(), from, to);

        // assert
        assert(availableRooms.size() == 2);
        assert(availableRooms.contains(room101));
        assert(availableRooms.contains(room102));
    }
    
    @Test
    void findAvailableRooms_WhenOneRoomBookedOverlappingArrival_ShouldReturnOnlyTheOther() {
        // arrange
        Booking booking = new Booking();
        booking.setRoom(room101);
        booking.setArrivalDate(LocalDate.of(2026, 6, 2));
        booking.setDepartureDate(LocalDate.of(2026, 6, 6));
        entityManager.persistAndFlush(booking);

        // act
        LocalDate from = LocalDate.of(2026, 6, 1);
        LocalDate to = LocalDate.of(2026, 6, 5);
        List<Room> availableRooms = roomRepository.findAvailableRooms(standardRoomType.getId(), from, to);

        // assert
        assert(availableRooms.size() == 1);
        assert(availableRooms.contains(room102));
    }

    @Test
    void findAvailableRooms_WhenOneRoomBookedOverlappingDeparture_ShouldReturnOnlyTheOther() {
        // arrange
        Booking booking = new Booking();
        booking.setRoom(room101);
        booking.setArrivalDate(LocalDate.of(2026, 6, 2));
        booking.setDepartureDate(LocalDate.of(2026, 6, 6));
        entityManager.persistAndFlush(booking);

        // act
        LocalDate from = LocalDate.of(2026, 6, 4);
        LocalDate to = LocalDate.of(2026, 6, 7);
        List<Room> availableRooms = roomRepository.findAvailableRooms(standardRoomType.getId(), from, to);

        // assert
        assert(availableRooms.size() == 1);
        assert(availableRooms.contains(room102));
    }

    @Test
    void findAvailableRooms_WhenBookingEndsOnArrivalDate_ShouldReturnRoomAsAvailable() {
        // arrange
        Booking booking = new Booking();
        booking.setRoom(room101);
        booking.setArrivalDate(LocalDate.of(2026, 5, 28));
        booking.setDepartureDate(LocalDate.of(2026, 6, 1)); // Ends on arrival date
        entityManager.persistAndFlush(booking);

        // act
        LocalDate from = LocalDate.of(2026, 6, 1);
        LocalDate to = LocalDate.of(2026, 6, 5);
        List<Room> availableRooms = roomRepository.findAvailableRooms(standardRoomType.getId(), from, to);

        // assert
        assert(availableRooms.size() == 2);
        assert(availableRooms.contains(room101));
        assert(availableRooms.contains(room102));
    }

    @Test
    void findAvailableRooms_WhenBookingStartsOnDepartureDate_ShouldReturnRoomAsAvailable() {
        // arrange
        Booking booking = new Booking();
        booking.setRoom(room101);
        booking.setArrivalDate(LocalDate.of(2026, 6, 5)); // Starts on departure date
        booking.setDepartureDate(LocalDate.of(2026, 6, 10)); 
        entityManager.persistAndFlush(booking);

        // act
        LocalDate from = LocalDate.of(2026, 6, 1);
        LocalDate to = LocalDate.of(2026, 6, 5);
        List<Room> availableRooms = roomRepository.findAvailableRooms(standardRoomType.getId(), from, to);

        // assert
        assert(availableRooms.size() == 2);
        assert(availableRooms.contains(room101));
        assert(availableRooms.contains(room102));
    }
}