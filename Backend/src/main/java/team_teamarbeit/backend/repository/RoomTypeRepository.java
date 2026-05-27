package team_teamarbeit.backend.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team_teamarbeit.backend.entity.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
    Page<RoomType> findByNameContainingIgnoreCase(String filter, Pageable pageable);
}
