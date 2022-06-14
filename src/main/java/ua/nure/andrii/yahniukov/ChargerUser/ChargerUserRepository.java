package ua.nure.andrii.yahniukov.ChargerUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChargerUserRepository extends JpaRepository<ChargerUserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<ChargerUserEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
