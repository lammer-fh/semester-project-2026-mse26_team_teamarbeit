package team_teamarbeit.backend.repository;

import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.Guest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    
}
