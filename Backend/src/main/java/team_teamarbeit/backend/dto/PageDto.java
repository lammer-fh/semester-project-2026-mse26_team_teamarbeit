package team_teamarbeit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private int size;
    private long totalElements;
    private int totalPages;
    private int number;
}
