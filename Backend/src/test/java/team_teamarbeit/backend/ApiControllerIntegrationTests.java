package team_teamarbeit.backend;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team_teamarbeit.backend.entity.Booking;
import team_teamarbeit.backend.entity.Guest;
import team_teamarbeit.backend.entity.Room;
import team_teamarbeit.backend.entity.RoomExtra;
import team_teamarbeit.backend.entity.RoomImage;
import team_teamarbeit.backend.entity.RoomType;
import team_teamarbeit.backend.enums.BookingStatusType;
import team_teamarbeit.backend.repository.BookingRepository;
import team_teamarbeit.backend.repository.GuestRepository;
import team_teamarbeit.backend.repository.RoomExtraRepository;
import team_teamarbeit.backend.repository.RoomImageRepository;
import team_teamarbeit.backend.repository.RoomRepository;
import team_teamarbeit.backend.repository.RoomTypeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerIntegrationTests {
    private static final LocalDate ARRIVAL = LocalDate.now().plusDays(10);
    private static final LocalDate DEPARTURE = ARRIVAL.plusDays(3);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomExtraRepository roomExtraRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    private Room room;
    private Guest guest;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();
        roomImageRepository.deleteAll();
        roomTypeRepository.deleteAll();
        roomExtraRepository.deleteAll();
        guestRepository.deleteAll();

        RoomExtra wifi = roomExtraRepository.save(RoomExtra.builder().name("WIFI").build());
        RoomExtra balcony = roomExtraRepository.save(RoomExtra.builder().name("BALCONY").build());

        RoomType roomType = RoomType.builder()
            .name("Junior Suite")
            .description("Bright suite with city view")
            .pricePerNight(new BigDecimal("120.00"))
            .roomExtras(new HashSet<>(Set.of(wifi, balcony)))
            .build();
        roomType = roomTypeRepository.save(roomType);

        RoomImage image = roomImageRepository.save(RoomImage.builder()
            .imagePath("/images/rooms/junior-suite.jpg")
            .roomType(roomType)
            .build());
        roomType.getRoomImages().add(image);

        room = roomRepository.save(Room.builder()
            .roomNumber("101")
            .roomType(roomType)
            .build());

        guest = guestRepository.save(Guest.builder()
            .mail("guest@example.com")
            .firstName("Ada")
            .lastName("Lovelace")
            .build());
    }

    @Test
    void getRoomsReturnsSpecShapeWithPagination() throws Exception {
        mockMvc.perform(get("/rooms")
                .param("page", "1")
                .param("size", "5")
                .param("filter", "suite")
                .param("sort", "pricePerNight"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rooms", hasSize(1)))
            .andExpect(jsonPath("$.rooms[0].id").value(room.getId().toString()))
            .andExpect(jsonPath("$.rooms[0].name").value("Junior Suite"))
            .andExpect(jsonPath("$.rooms[0].description").value("Bright suite with city view"))
            .andExpect(jsonPath("$.rooms[0].pricePerNight").value(120.0))
            .andExpect(jsonPath("$.rooms[0].extras", containsInAnyOrder("WIFI", "BALCONY")))
            .andExpect(jsonPath("$.rooms[0].imagePaths[0]").value("/images/rooms/junior-suite.jpg"))
            .andExpect(jsonPath("$.page.size").value(5))
            .andExpect(jsonPath("$.page.totalElements").value(1))
            .andExpect(jsonPath("$.page.number").value(1));
    }

    @Test
    void getRoomAvailabilityReturnsFalseWhenBookingOverlaps() throws Exception {
        bookingRepository.save(Booking.builder()
            .arrivalDate(ARRIVAL)
            .departureDate(DEPARTURE)
            .breakfast(false)
            .status(BookingStatusType.CONFIRMED)
            .totalPrice(new BigDecimal("360.00"))
            .guest(guest)
            .room(room)
            .build());

        mockMvc.perform(get("/rooms/{id}/availability", room.getId())
                .param("from", ARRIVAL.plusDays(1).toString())
                .param("to", DEPARTURE.plusDays(1).toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void createBookingWithGuestCreatesBookingAndCalculatesTotalPrice() throws Exception {
        String request = """
            {
              "roomId": "%s",
              "from": "%s",
              "to": "%s",
              "breakfast": true,
              "guest": {
                "firstName": "Grace",
                "lastName": "Hopper",
                "email": "grace@example.com"
              }
            }
            """.formatted(room.getId(), ARRIVAL, DEPARTURE);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.roomId").value(room.getId().toString()))
            .andExpect(jsonPath("$.breakfast").value(true))
            .andExpect(jsonPath("$.duration").value(3))
            .andExpect(jsonPath("$.totalPrice").value(360.0));
    }

    @Test
    void createBookingReturnsConflictWhenRoomIsNotAvailable() throws Exception {
        bookingRepository.save(Booking.builder()
            .arrivalDate(ARRIVAL)
            .departureDate(DEPARTURE)
            .breakfast(false)
            .status(BookingStatusType.CONFIRMED)
            .totalPrice(new BigDecimal("360.00"))
            .guest(guest)
            .room(room)
            .build());

        String request = """
            {
              "roomId": "%s",
              "from": "%s",
              "to": "%s",
              "breakfast": false,
              "guestId": "%s"
            }
            """.formatted(room.getId(), ARRIVAL.plusDays(1), DEPARTURE.plusDays(1), guest.getId());

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.errorMessage").exists());
    }

    @Test
    void guestEndpointsCreateUpdateListBookingsAndDeleteGuest() throws Exception {
        String createRequest = """
            {
              "email": "linus@example.com",
              "firstName": "Linus",
              "lastName": "Torvalds"
            }
            """;

        String response = mockMvc.perform(post("/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("linus@example.com"))
            .andReturn()
            .getResponse()
            .getContentAsString();

        String guestId = extractJsonStringValue(response, "id");

        String updateRequest = """
            {
              "email": "linus.updated@example.com",
              "firstName": "Linus",
              "lastName": "Updated"
            }
            """;

        mockMvc.perform(put("/guests/{id}", guestId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.lastName").value("Updated"));

        mockMvc.perform(get("/guests/{id}/bookings", guestId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bookings", hasSize(0)));

        mockMvc.perform(delete("/guests/{id}", guestId))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/guests/{id}", guestId))
            .andExpect(status().isNotFound());
    }

    private String extractJsonStringValue(String json, String fieldName) {
        Matcher matcher = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        if (!matcher.find()) {
            throw new IllegalStateException("Could not find JSON field " + fieldName + " in response: " + json);
        }
        return matcher.group(1);
    }
}
