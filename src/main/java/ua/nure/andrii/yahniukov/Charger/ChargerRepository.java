package ua.nure.andrii.yahniukov.Charger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargerRepository extends JpaRepository<ChargerEntity, Long> {
    boolean existsByCode(String code);

    List<ChargerEntity> findAllByOwner(ChargerUserEntity chargerUser);

    Optional<ChargerEntity> findByCode(String code);
}
