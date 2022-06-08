package ua.nure.andrii.yahniukov.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(Long id);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
