package team_teamarbeit.backend.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
