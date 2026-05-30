package team_teamarbeit.backend.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Room_Types")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;

    @Column(name = "cover_image_path")
    private String coverImagePath;

    @ManyToMany
    @JoinTable(
            name = "room_type_extras",
            joinColumns = @JoinColumn(name = "room_type_id"),
            inverseJoinColumns = @JoinColumn(name = "room_extras_name")
    )
    @Builder.Default
    private Set<RoomExtra> roomExtras = new HashSet<>();

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<RoomImage> roomImages = new HashSet<>();

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Room> rooms = new HashSet<>();
}
