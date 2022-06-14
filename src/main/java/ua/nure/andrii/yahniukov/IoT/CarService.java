package ua.nure.andrii.yahniukov.IoT;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.IoT.dto.CarDto;
import ua.nure.andrii.yahniukov.IoT.dto.FormCarDto;
import ua.nure.andrii.yahniukov.IoT.dto.FormVinCodeDto;
import ua.nure.andrii.yahniukov.User.UserEntity;
import ua.nure.andrii.yahniukov.User.UserRepository;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CarEntity findByVinCode(String vinCode) {
        return carRepository
                .findByVinCode(vinCode)
                .orElseThrow(() -> new BadRequestException("Car with VIN code " + vinCode + " not found"));
    }

    public void create(FormCarDto car) {
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

    public CarDto getByVinCode(String vinCode) {
        return CarDto.fromCar(findByVinCode(vinCode));
    }

    public List<CarDto> getAllForUser(String email) {
        UserEntity owner = userService.findByEmail(email);
        return carRepository
                .findAllByOwner(owner)
                .stream()
                .map(CarDto::fromCar)
                .toList();
    }

    public void addByVinCode(String email, FormVinCodeDto vinCode) {
        UserEntity user = userService.findByEmail(email);
        CarEntity car = findByVinCode(vinCode.getVinCode());
        car.setOwner(user);
        user.getCars().add(car);
        userRepository.save(user);
        carRepository.save(car);
    }

    public void deleteByVinCode(String email, String vinCode) {
        UserEntity user = userService.findByEmail(email);
        CarEntity car = findByVinCode(vinCode);
        car.setOwner(null);
        user.getCars().remove(car);
        userRepository.save(user);
        carRepository.save(car);
    }
}