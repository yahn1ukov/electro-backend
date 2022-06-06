package ua.nure.andrii.yahniukov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.ComplaintUserChargerEntity;

@Repository
public interface ComplaintUserChargerRepository extends JpaRepository<ComplaintUserChargerEntity, Long> {
}
