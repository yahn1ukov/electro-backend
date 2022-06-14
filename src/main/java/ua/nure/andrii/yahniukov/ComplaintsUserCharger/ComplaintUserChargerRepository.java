package ua.nure.andrii.yahniukov.ComplaintsUserCharger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintUserChargerRepository extends JpaRepository<ComplaintUserChargerEntity, Long> {
}
