package ua.nure.andrii.yahniukov.ComplaintsUserStation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintUserStationRepository extends JpaRepository<ComplaintUserStationEntity, Long> {
}
