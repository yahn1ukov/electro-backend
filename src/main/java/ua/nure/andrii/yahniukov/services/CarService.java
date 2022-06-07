package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.IoT.CarDto;
import ua.nure.andrii.yahniukov.models.dto.helpers.VinCodeDto;
import ua.nure.andrii.yahniukov.models.entities.IoT.CarEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.IoT.CarRepository;
import ua.nure.andrii.yahniukov.repositories.users.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CarEntity findCarByVinCode(VinCodeDto vinCode) {
        return carRepository
                .findByVinCode(vinCode.getVinCode())
                .orElseThrow(() -> new BadRequestException("Car with VIN code " + vinCode + " not found"));
    }

    /*
     * Для IoT: зчитування та створення електромобіля
     */
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

    public CarEntity findCarByVinCode(String vinCode) {
        return carRepository
                .findByVinCode(vinCode)
                .orElseThrow(() -> new BadRequestException("Car with VIN code " + vinCode + " not found"));
    }

    /*
     * Для власників електромобілів: перегляд інформації про електромобіль
     */
    public CarDto getCarByVinCode(String vinCode) {
        return CarDto.fromCar(findCarByVinCode(vinCode));
    }

    /*
     * Для власників електромобілів: перегляд усіх електромобілів
     */
    public List<CarDto> getAllUserCars(Long userId) {
        UserEntity owner = userService.findUserById(userId);
        return carRepository
                .findAllByOwner(owner)
                .stream()
                .map(CarDto::fromCar)
                .collect(Collectors.toList());
    }

    /*
     * Для власників електромобілів: прив'язати електромобіль
     */
    public void addCarToUserByVinCode(Long userId, VinCodeDto vinCode) {
        UserEntity user = userService.findUserById(userId);
        CarEntity car = findCarByVinCode(vinCode);
        car.setOwner(user);
        user.getCars().add(car);
        userRepository.save(user);
        carRepository.save(car);
    }

    /*
     * Для власників електромобілів: відв'язати електромобіль
     */
    public void deleteCarFromUserByVinCode(Long userId, String vinCode) {
        UserEntity user = userService.findUserById(userId);
        CarEntity car = findCarByVinCode(vinCode);
        car.setOwner(null);
        user.getCars().remove(car);
        userRepository.save(user);
        carRepository.save(car);
    }
}