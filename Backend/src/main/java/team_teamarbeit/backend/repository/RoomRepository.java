package team_teamarbeit.backend.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    
}
