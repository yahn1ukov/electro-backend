package ua.nure.andrii.yahniukov.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    boolean existsByName(String name);

    List<StationEntity> findAllByOwner(StationUserEntity stationUser);

    Optional<StationEntity> findByName(String name);
}
