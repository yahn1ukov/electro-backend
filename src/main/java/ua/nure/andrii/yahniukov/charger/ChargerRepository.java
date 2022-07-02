package ua.nure.andrii.yahniukov.charger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;

import java.util.List;

@Repository
public interface ChargerRepository extends JpaRepository<ChargerEntity, Long> {
    boolean existsByCode(String code);

    List<ChargerEntity> findAllByOwner(ChargerUserEntity chargerUser);
}
