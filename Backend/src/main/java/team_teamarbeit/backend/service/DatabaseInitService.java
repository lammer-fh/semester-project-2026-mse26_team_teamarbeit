package team_teamarbeit.backend.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.entity.RoomExtra;
import team_teamarbeit.backend.entity.RoomType;
import team_teamarbeit.backend.repository.RoomExtraRepository;
import team_teamarbeit.backend.repository.RoomRepository;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@Service
public class DatabaseInitService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomExtraRepository roomExtraRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Iterable<RoomExtra> initRoomExtras() {
        if (roomExtraRepository.count() > 0) {
            return roomExtraRepository.findAll(); // Room extras already initialized
        }

        var extraWifi = RoomExtra.builder().name("WiFi").build();
        var extraBathtub = RoomExtra.builder().name("Bathtub").build();
        var extraBalcony = RoomExtra.builder().name("Balcony").build();
        return roomExtraRepository.saveAll(Set.of(extraWifi, extraBathtub, extraBalcony));
    }

    public Iterable<RoomType> initRoomTypes() {
        if (roomTypeRepository.count() > 0) {
            return roomTypeRepository.findAll(); // Room types already initialized
        }

        var extras = initRoomExtras();

        var defaultExtraNames = Set.of("WiFi", "Bathtub");
        var defaultExtras = StreamSupport.stream(extras.spliterator(), false)
            .filter(e -> defaultExtraNames.contains(e.getName()))
            .collect(Collectors.toSet());
        var roomTypeDefault = RoomType.builder()
                .name("Standard")
                .description("A standard room with basic amenities.")
                .pricePerNight(100.0)
                .roomExtras(defaultExtras)
                .build();


        var deluxExtraNames = Set.of("WiFi", "Bathtub", "Balcony");
        var deluxeExtras = StreamSupport.stream(extras.spliterator(), false)
            .filter(e -> deluxExtraNames.contains(e.getName()))
            .collect(Collectors.toSet());
        var roomTypeDeluxe = RoomType.builder()
                .name("Deluxe")
                .description("A deluxe room with additional amenities.")
                .pricePerNight(150.0)
                .roomExtras(deluxeExtras)
                .build();

        return roomTypeRepository.saveAll(Set.of(roomTypeDefault, roomTypeDeluxe));
    }

    public Iterable<Room> initRooms() {
        if (roomRepository.count() > 0) {
            return roomRepository.findAll(); // Rooms already initialized
        }

        var roomTypes = (Collection<RoomType>) initRoomTypes();
        var standardRoomType = roomTypes.stream()
            .filter(rt -> rt.getName().equals("Standard"))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Standard room type not found"));

        var deluxeRoomType = roomTypes.stream()
            .filter(rt -> rt.getName().equals("Deluxe"))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Deluxe room type not found"));

        var room1 = Room.builder()
                .roomNumber("101")
                .roomType(standardRoomType)
                .build();
        var room2 = Room.builder()
                .roomNumber("102")
                .roomType(standardRoomType)
                .build();
        var room3 = Room.builder()
                .roomNumber("103")
                .roomType(deluxeRoomType)
                .build();

        return roomRepository.saveAll(Set.of(room1, room2, room3));
    }
}
