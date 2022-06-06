package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.entities.CarEntity;
import ua.nure.andrii.yahniukov.models.entities.UserEntity;
import ua.nure.andrii.yahniukov.repositories.CarRepository;
import ua.nure.andrii.yahniukov.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarEntity getCarByVinCode(String vinCode) {
        return carRepository
                .findByVinCode(vinCode)
                .orElseThrow(() -> new BadRequestException("Car with VIN code: " + vinCode + " not found"));
    }

    public List<CarEntity> getAllUserCars(Long userId) {
        UserEntity owner = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        return carRepository.findAllByOwner(owner);
    }

    public void createCar(CarEntity car) {
        if (carRepository.existsByVinCode(car.getVinCode())) {
            throw new BadRequestException("Car with VIN code: " + car.getVinCode() + " already exists");
        }
        carRepository.save(CarEntity.builder()
                .vinCode(car.getVinCode())
                .owner(null)
                .name(car.getName())
                .model(car.getModel())
                .mileage(car.getMileage())
                .typeConnector(car.getTypeConnector())
                .percentageOfCharge(car.getPercentageOfCharge())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build());
    }

    public void addCarToUserByVinCode(Long userId, String vinCode) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        CarEntity car = carRepository
                .findByVinCode(vinCode)
                .orElseThrow(() -> new BadRequestException("Car with VIN code: " + vinCode + " not found"));

        car.setOwner(user);
        user.getCars().add(car);

        userRepository.save(user);
        carRepository.save(car);
    }

    public void deleteCarFromUserByVinCode(Long userId, String vinCode) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        CarEntity car = carRepository
                .findByVinCode(vinCode)
                .orElseThrow(() -> new BadRequestException("Car with VIN code: " + vinCode + " not found"));

        car.setOwner(null);
        user.getCars().remove(car);

        userRepository.save(user);
        carRepository.save(car);
    }
}
