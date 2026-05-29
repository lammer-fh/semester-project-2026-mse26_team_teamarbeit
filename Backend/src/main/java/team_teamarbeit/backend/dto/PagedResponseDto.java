package team_teamarbeit.backend.dto;

import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
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

    public static <T,U> PagedResponseDto<U> fromPage(Page<T> page, Function<T,U> mapper) {
        return PagedResponseDto.<U>builder()
            .items(page.getContent().stream().map(mapper).toList())
            .pageNumber(page.getNumber())
            .pageSize(page.getSize())
            .totalItems(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .isLast(page.isLast())
            .build();
    }
}
