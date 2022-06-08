package ua.nure.andrii.yahniukov.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;

import java.util.Optional;

public interface StationUserRepository extends JpaRepository<StationUserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<StationUserEntity> findByEmail(String email);
}
