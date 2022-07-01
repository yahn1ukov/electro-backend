package ua.nure.andrii.yahniukov.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.station.FormFreePlaceDto;
import ua.nure.andrii.yahniukov.dto.station.FormStationDto;
import ua.nure.andrii.yahniukov.dto.station.StationDto;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StationService {
    private final StationRepository stationRepository;
    private final StationUserService stationUserService;

    public StationEntity findByName(String name) {
        return stationRepository
                .findByName(name)
                .orElseThrow(() -> new BadRequestException("Station with name " + name + " not found"));
    }

    public StationEntity findById(Long id) {
        return stationRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Station with id " + id + " not found"));
    }

    public void create(String email, FormStationDto station) {
        if (stationRepository.existsByName(station.getName())) {
            throw new BadRequestException("Station with name " + station.getName() + " already exists");
        }
        StationUserEntity stationUser = stationUserService.findByEmail(email);
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
    }

    public List<StationDto> getAllForStationUser(String email) {
        StationUserEntity stationUser = stationUserService.findByEmail(email);
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

    public void changeFreePlace(String name, FormFreePlaceDto freePlace) {
        StationEntity station = findByName(name);
        station.setFreePlace(freePlace.getFreePlace());
        stationRepository.save(station);
    }

    public StationDto getByName(String name) {
        return StationDto.fromStation(findByName(name));
    }

    public void deleteByName(String email, String name) {
        StationUserEntity stationUser = stationUserService.findByEmail(email);
        StationEntity station = findByName(name);
        stationUser.getStations().remove(station);
        stationRepository.delete(station);
    }

    public List<StationDto> getAllForCar(Long latitude, Long longitude, String carName, String carModel, Integer radius) {
        if (
                latitude < -90.0 || latitude > 90.0 ||
                        longitude < -180.0 || longitude > 180.0
        ) {
            throw new BadRequestException("Something was wrong");
        }
        return stationRepository
                .findAll()
                .stream()
                .filter(station -> StationDto.isRadius(station, latitude, longitude, radius))
                .filter(StationDto::isFreePlace)
                .filter(station -> StationDto.isName(station, carName))
                .filter(station -> StationDto.isModel(station, carModel))
                .map(StationDto::fromStation)
                .collect(Collectors.toList());
    }
}
