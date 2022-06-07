package ua.nure.andrii.yahniukov.repositories.complaints;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;

@Repository
public interface ComplaintUserStationRepository extends JpaRepository<ComplaintUserStationEntity, Long> {
}
