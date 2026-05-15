package team_teamarbeit.backend.entity;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Room_Extras")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomExtra {
    @Id
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roomExtras")
    private Set<RoomType> roomTypes;
}
