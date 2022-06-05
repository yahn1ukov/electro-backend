package ua.nure.andrii.yahniukov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(Long id);

    boolean existsByEmail(String email);
}
