package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormFreePlace;
import ua.nure.andrii.yahniukov.models.dto.forms.FormStationDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.StationDto;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;
import ua.nure.andrii.yahniukov.repositories.maintenances.StationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StationService {
    private final StationRepository stationRepository;
    private final UserService userService;

    public StationEntity findStationById(Long stationId) {
        return stationRepository
                .findById(stationId)
                .orElseThrow(() -> new BadRequestException("Station with id " + stationId + " not found"));
    }

    /*
     * Для власників СТО: створення СТО від виробника
     */
    public void createStation(Long stationUserId, FormStationDto station) {
        if (stationRepository.existsByName(station.getName())) {
            throw new BadRequestException("Station with name " + station.getName() + " already exists");
        }
        StationUserEntity stationUser = userService.findStationUserById(stationUserId);
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

    /*
     * Для власників СТО: отримання усіх СТО виробника
     */
    public List<StationDto> getAllStationUserChargers(Long stationUserId) {
        StationUserEntity stationUser = userService.findStationUserById(stationUserId);
        return stationRepository
                .findAllByOwner(stationUser)
                .stream()
                .map(StationDto::fromStation)
                .collect(Collectors.toList());
    }

    /*
     * Для власників електромобілів: отримання усіх СТО
     */
    public List<StationDto> getAllStations() {
        return stationRepository
                .findAll()
                .stream()
                .map(StationDto::fromStation)
                .collect(Collectors.toList());
    }

    /*
     * Для власників СТО: кількість вільних місць у СТО
     */
    public void changeFreePlace(Long stationId, FormFreePlace freePlace) {
        StationEntity station = findStationById(stationId);
        station.setFreePlace(freePlace.getFreePlace());
        stationRepository.save(station);
    }

    /*
     * Для власників електромобілів: отримання однієї СТО
     */
    public StationDto getStationById(Long stationId) {
        return StationDto.fromStation(findStationById(stationId));
    }

    /*
     * Для власників СТО: видалення СТО виробника
     */
    public void deleteStationById(Long stationUserId, Long stationId) {
        StationUserEntity stationUser = userService.findStationUserById(stationUserId);
        StationEntity station = findStationById(stationId);
        stationUser.getStations().remove(station);
        stationRepository.delete(station);
    }
}
