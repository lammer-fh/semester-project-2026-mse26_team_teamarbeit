package team_teamarbeit.backend.dto;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRoomRequestDto {
    @NotNull(message = "roomId is required")
    private UUID roomId;

    @NotNull(message = "from is required")
    private LocalDate from;

    @NotNull(message = "to is required")
    private LocalDate to;

    private boolean breakfast;

    private UUID userId;

    @Valid
    private GuestRequestDto guest;

    @AssertTrue(message = "Either userId or guest is required")
    public boolean isGuestInformationPresent() {
        return userId != null || guest != null;
    }

    @AssertTrue(message = "from may not be in the past and to must be after from")
    public boolean isDateRangeValid() {
        return from == null || to == null
            || (!from.isBefore(LocalDate.now()) && to.isAfter(from));
    }

    @Data
    public static class GuestRequestDto {
        @NotBlank(message = "guest.firstName is required")
        private String firstName;

        @NotBlank(message = "guest.lastName is required")
        private String lastName;

        @NotBlank(message = "guest.email is required")
        @Email(message = "guest.email must be valid")
        private String email;
    }
}
