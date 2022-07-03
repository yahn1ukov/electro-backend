package ua.nure.andrii.yahniukov.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.dto.station.FormFreePlaceDto;
import ua.nure.andrii.yahniukov.dto.station.FormStationDto;
import ua.nure.andrii.yahniukov.dto.station.StationDto;
import ua.nure.andrii.yahniukov.exception.station.StationAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.station.StationFreePlaceMoreThanAllPlaceException;
import ua.nure.andrii.yahniukov.exception.station.StationNotFoundException;
import ua.nure.andrii.yahniukov.iot.CarEntity;
import ua.nure.andrii.yahniukov.iot.CarService;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StationService {
    private final StationRepository stationRepository;
    private final StationUserService stationUserService;
    private final CarService carService;

    public StationEntity findById(Long id) {
        return stationRepository
                .findById(id)
                .orElseThrow(StationNotFoundException::new);
    }

    public StationEntity findByName(String name) {
        return stationRepository
                .findByName(name)
                .orElseThrow(StationNotFoundException::new);
    }

    public SuccessMessageDto create(Long userId, FormStationDto station) {
        if (stationRepository.existsByName(station.getName())) {
            throw new StationAlreadyExistsException();
        }
        StationUserEntity stationUser = stationUserService.findById(userId);
        StationEntity createdStation = stationRepository.save(
                StationEntity.builder()
                        .name(station.getName())
                        .country(station.getCountry())
                        .city(station.getCity())
                        .street(station.getStreet())
                        .zipCode(station.getZipCode())
                        .latitude(station.getLatitude())
                        .longitude(station.getLongitude())
                        .allPlace(station.getAllPlace())
                        .freePlace(station.getFreePlace())
                        .carModel(station.getCarModel())
                        .carName(station.getCarName())
                        .owner(stationUser)
                        .middlePriceForPerHour(station.getMiddlePriceForPerHour())
                        .timeFrom(station.getTimeFrom())
                        .timeTo(station.getTimeTo())
                        .build()
        );
        stationUser.getStations().add(createdStation);
        return SuccessMessageDto.builder().message("Service station successfully created").build();
    }

    public List<StationDto> getAllForStationUser(Long userId) {
        StationUserEntity stationUser = stationUserService.findById(userId);
        return stationRepository
                .findAllByOwner(stationUser)
                .stream()
                .map(StationDto::fromStation)
                .toList();
    }

    public List<StationDto> getAll() {
        return stationRepository
                .findAll()
                .stream()
                .map(StationDto::fromStation)
                .toList();
    }

    public SuccessMessageDto changeFreePlace(String name, FormFreePlaceDto freePlace) {
        StationEntity station = findByName(name);
        if (freePlace.getFreePlace() > station.getAllPlace()) {
            throw new StationFreePlaceMoreThanAllPlaceException();
        }
        station.setFreePlace(freePlace.getFreePlace());
        stationRepository.save(station);
        return SuccessMessageDto.builder().message("Count of free places changed").build();
    }

    public StationDto get(Long id) {
        return StationDto.fromStation(findById(id));
    }

    public void delete(Long userId, Long stationId) {
        StationUserEntity stationUser = stationUserService.findById(userId);
        StationEntity station = findById(stationId);
        stationUser.getStations().remove(station);
        stationRepository.deleteById(stationId);
    }

    public List<StationDto> getAllForCar(String vinCode) {
        CarEntity car = carService.findByVinCode(vinCode);
        return stationRepository
                .findAll()
                .stream()
                .filter(station -> station.getFreePlace() > 0 && station.getFreePlace() <= station.getAllPlace())
                .filter(station -> station.getCarName().equals(car.getName()))
                .filter(station -> station.getCarModel().equals(car.getModel()))
                .filter(station -> carService.calculateRadius(
                                car.getLatitude(),
                                car.getLongitude(),
                                station.getLatitude(),
                                station.getLongitude()
                        ) <= 50
                )
                .map(StationDto::fromStation)
                .toList();
    }
}