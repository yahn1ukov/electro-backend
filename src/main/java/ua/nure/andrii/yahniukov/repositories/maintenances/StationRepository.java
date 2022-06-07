package ua.nure.andrii.yahniukov.repositories.maintenances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
