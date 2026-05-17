package team_teamarbeit.backend.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Page<Room> findByRoomType_NameContainingIgnoreCase(String filter, Pageable pageable);

    List<Room> findByRoomType_NameContainingIgnoreCase(String filter, Sort sort);
}
