package ua.nure.andrii.yahniukov.repositories.maintenances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    boolean existsByName(String stationName);

    List<StationEntity> findAllByOwner(StationUserEntity stationUser);
}
