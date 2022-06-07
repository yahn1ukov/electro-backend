package ua.nure.andrii.yahniukov.repositories.IoT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.models.entities.IoT.CarEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Optional<CarEntity> findByVinCode(String vinCode);

    boolean existsByVinCode(String vinCode);

    List<CarEntity> findAllByOwner(UserEntity owner);
}
