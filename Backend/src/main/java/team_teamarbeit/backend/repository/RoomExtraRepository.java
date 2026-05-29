package team_teamarbeit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.RoomExtra;

@Repository
public interface RoomExtraRepository extends JpaRepository<RoomExtra, String> {
    
}
