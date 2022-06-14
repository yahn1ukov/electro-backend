package ua.nure.andrii.yahniukov.StationUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationUserRepository extends JpaRepository<StationUserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<StationUserEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
