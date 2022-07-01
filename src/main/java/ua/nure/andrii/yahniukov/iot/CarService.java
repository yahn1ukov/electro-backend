package ua.nure.andrii.yahniukov.iot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.iot.AddCarDto;
import ua.nure.andrii.yahniukov.dto.iot.CarDto;
import ua.nure.andrii.yahniukov.dto.iot.VinCodeDto;
import ua.nure.andrii.yahniukov.exceptions.iot.CarAlreadyExistsException;
import ua.nure.andrii.yahniukov.exceptions.iot.CarNotFoundException;
import ua.nure.andrii.yahniukov.user.UserEntity;
import ua.nure.andrii.yahniukov.user.UserRepository;
import ua.nure.andrii.yahniukov.user.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CarEntity findById(Long id) {
        return carRepository
                .findById(id)
                .orElseThrow(CarNotFoundException::new);
    }

    public CarEntity findByVinCode(String vinCode) {
        return carRepository
                .findByVinCode(vinCode)
                .orElseThrow(CarNotFoundException::new);
    }

    public void create(AddCarDto car) {
        if (carRepository.existsByVinCode(car.getVinCode())) {
            throw new CarAlreadyExistsException();
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

    public CarDto get(Long id) {
        return CarDto.fromCar(findById(id));
    }

    public List<CarDto> getAll(Long userId) {
        UserEntity owner = userService.findById(userId);
        return carRepository
                .findAllByOwner(owner)
                .stream()
                .map(CarDto::fromCar)
                .toList();
    }

    public void addByVinCode(Long userId, VinCodeDto vinCode) {
        UserEntity user = userService.findById(userId);
        CarEntity car = findByVinCode(vinCode.getVinCode());
        car.setOwner(user);
        user.getCars().add(car);
        userRepository.save(user);
        carRepository.save(car);
    }

    public void delete(Long userId, Long carId) {
        UserEntity user = userService.findById(userId);
        CarEntity car = findById(carId);
        car.setOwner(null);
        user.getCars().remove(car);
        userRepository.save(user);
        carRepository.save(car);
    }
}