package team_teamarbeit.backend.repository;

import org.springframework.stereotype.Repository;
import team_teamarbeit.backend.entity.Guest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    Optional<Guest> findByMailIgnoreCase(String mail);

    boolean existsByMailIgnoreCase(String mail);

    boolean existsByMailIgnoreCaseAndIdNot(String mail, UUID id);
}
