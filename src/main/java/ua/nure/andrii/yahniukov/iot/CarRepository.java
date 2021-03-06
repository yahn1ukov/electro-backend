package ua.nure.andrii.yahniukov.iot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.andrii.yahniukov.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Optional<CarEntity> findByVinCode(String vinCode);

    boolean existsByVinCode(String vinCode);

    List<CarEntity> findAllByOwner(UserEntity owner);
}
