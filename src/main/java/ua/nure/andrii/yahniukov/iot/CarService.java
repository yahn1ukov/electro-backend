package ua.nure.andrii.yahniukov.iot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.iot.CarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormAddCarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormUpdateCarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormVinCodeDto;
import ua.nure.andrii.yahniukov.exception.iot.CarAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.iot.CarNotFoundException;
import ua.nure.andrii.yahniukov.user.UserEntity;
import ua.nure.andrii.yahniukov.user.UserRepository;
import ua.nure.andrii.yahniukov.user.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    public final double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
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

    public void create(FormAddCarDto car) {
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

    public CarDto getById(Long id) {
        return CarDto.fromCar(findById(id));
    }

    public CarDto getByVinCode(String vinCode) {
        return CarDto.fromCar(findByVinCode(vinCode));
    }

    public List<CarDto> getAll(Long userId) {
        UserEntity owner = userService.findById(userId);
        return carRepository
                .findAllByOwner(owner)
                .stream()
                .map(CarDto::fromCar)
                .toList();
    }

    public void addByVinCode(Long userId, FormVinCodeDto vinCode) {
        UserEntity user = userService.findById(userId);
        CarEntity car = findByVinCode(vinCode.getVinCode());
        car.setOwner(user);
        user.getCars().add(car);
        userRepository.save(user);
        carRepository.save(car);
    }

    public void updateByVinCode(String vinCode, FormUpdateCarDto car) {
        CarEntity updateCar = findByVinCode(vinCode);
        updateCar.setLatitude(car.getLatitude());
        updateCar.setLongitude(car.getLongitude());
        updateCar.setPercentageOfCharge(car.getPercentageOfCharge());
        carRepository.save(updateCar);
    }

    public void delete(Long userId, Long carId) {
        UserEntity user = userService.findById(userId);
        CarEntity car = findById(carId);
        car.setOwner(null);
        user.getCars().remove(car);
        userRepository.save(user);
        carRepository.save(car);
    }

    public int calculateRadius(double carLat, double carLng,
                               double maintenanceLat, double maintenanceLng) {

        double latitudeDistance = Math.toRadians(carLat - maintenanceLat);
        double longitudeDistance = Math.toRadians(carLng - maintenanceLng);

        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(carLat)) * Math.cos(Math.toRadians(maintenanceLat))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}