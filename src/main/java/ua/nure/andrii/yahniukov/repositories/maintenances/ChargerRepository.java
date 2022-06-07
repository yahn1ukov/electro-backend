package ua.nure.andrii.yahniukov.repositories.maintenances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;

import java.util.List;

@Repository
public interface ChargerRepository extends JpaRepository<ChargerEntity, Long> {
    boolean existsByName(String name);

    List<ChargerEntity> findAllByOwner(ChargerUserEntity chargerUser);
}
