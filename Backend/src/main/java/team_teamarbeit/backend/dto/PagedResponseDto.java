package team_teamarbeit.backend.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagedResponseDto<T> {
    private List<T> items;
    private int pageNumber;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean isLast;
}
