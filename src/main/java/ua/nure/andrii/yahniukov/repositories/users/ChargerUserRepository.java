package ua.nure.andrii.yahniukov.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;

public interface ChargerUserRepository extends JpaRepository<ChargerUserEntity, Long> {
    boolean existsByEmail(String email);
}
