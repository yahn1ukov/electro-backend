package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.CarDto;
import ua.nure.andrii.yahniukov.models.dto.VinCodeDto;
import ua.nure.andrii.yahniukov.models.entities.IoT.CarEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.CarRepository;
import ua.nure.andrii.yahniukov.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarDto getCarByVinCode(String vinCode) {
        return CarDto.fromCar(
                carRepository
                        .findByVinCode(vinCode)
                        .orElseThrow(() -> new BadRequestException("Car with VIN code: " + vinCode + " not found"))
        );
    }

    public List<CarDto> getAllUserCars(Long userId) {
        UserEntity owner = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        return carRepository
                .findAllByOwner(owner)
                .stream()
                .map(CarDto::fromCar)
                .collect(Collectors.toList());
    }

    public void createCar(CarDto car) {
        if (carRepository.existsByVinCode(car.getVinCode())) {
            throw new BadRequestException("Car with VIN code: " + car.getVinCode() + " already exists");
        }
        carRepository.save(CarEntity.builder()
                .vinCode(car.getVinCode())
                .name(car.getName())
                .model(car.getModel())
                .mileage(car.getMileage())
                .typeConnector(car.getTypeConnector())
                .percentageOfCharge(car.getPercentageOfCharge())
                .build());
    }

    public void addCarToUserByVinCode(Long userId, VinCodeDto vinCode) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        CarEntity car = carRepository
                .findByVinCode(vinCode.getVinCode())
                .orElseThrow(() -> new BadRequestException("Car with VIN code: " + vinCode + " not found"));

        car.setOwner(user);
        user.getCars().add(car);

        userRepository.save(user);
        carRepository.save(car);
    }

    public void deleteCarFromUserByVinCode(Long userId, VinCodeDto vinCode) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        CarEntity car = carRepository
                .findByVinCode(vinCode.getVinCode())
                .orElseThrow(() -> new BadRequestException("Car with VIN code: " + vinCode + " not found"));

        car.setOwner(null);
        user.getCars().remove(car);

        userRepository.save(user);
        carRepository.save(car);
    }
}
