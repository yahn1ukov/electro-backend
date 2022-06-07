package ua.nure.andrii.yahniukov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;

public interface StationUserRepository extends JpaRepository<StationUserEntity, Long> {
    boolean existsByEmail(String email);
}
