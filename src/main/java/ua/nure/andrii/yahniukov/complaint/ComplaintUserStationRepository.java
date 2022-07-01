package ua.nure.andrii.yahniukov.complaint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintUserStationRepository extends JpaRepository<ComplaintUserStationEntity, Long> {
}
