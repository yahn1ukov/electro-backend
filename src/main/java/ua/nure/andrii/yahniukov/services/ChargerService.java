package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormChargerDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.ChargerDto;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.repositories.maintenances.ChargerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerService {
    private final ChargerRepository chargerRepository;
    private final UserService userService;
    public final double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public ChargerEntity findChargerById(Long chargerId) {
        return chargerRepository
                .findById(chargerId)
                .orElseThrow(() -> new BadRequestException("Charger with id " + chargerId + " not found"));
    }

    /*
     * Для виробників зарядних станцій: створення зарядной станцій від виробника
     */
    public void createCharger(Long chargerUserId, FormChargerDto charger) {
        if (chargerRepository.existsByName(charger.getName())) {
            throw new BadRequestException("Charger with name " + charger.getName() + " already exists");
        }
        ChargerUserEntity chargerUser = userService.findChargerUserById(chargerUserId);
        ChargerEntity createdCharger = chargerRepository.save(
                ChargerEntity.builder()
                        .name(charger.getName())
                        .country(charger.getCountry())
                        .city(charger.getCity())
                        .street(charger.getStreet())
                        .zipCode(charger.getZipCode())
                        .latitude(charger.getLatitude())
                        .longitude(charger.getLongitude())
                        .isFast(charger.getIsFast())
                        .isPay(charger.getIsPay())
                        .priceOfPerHour(charger.getPriceOfPerHour())
                        .owner(chargerUser)
                        .typeConnector(charger.getTypeConnector())
                        .timeFrom(charger.getTimeFrom())
                        .timeTo(charger.getTimeTo())
                        .build()
        );
        chargerUser.getChargers().add(createdCharger);
    }

    /*
     * Для виробників зарядних станцій: отримання усіх зарядних станцій виробника
     */
    public List<ChargerDto> getAllChargerUserChargers(Long chargerUserId) {
        ChargerUserEntity chargerUser = userService.findChargerUserById(chargerUserId);
        return chargerRepository
                .findAllByOwner(chargerUser)
                .stream()
                .map(ChargerDto::fromCharger)
                .collect(Collectors.toList());
    }

    /*
     * Для власників електромобілів: отримання усіх зарядних станцій
     */
    public List<ChargerDto> getAllChargers() {
        return chargerRepository
                .findAll()
                .stream()
                .map(ChargerDto::fromCharger)
                .collect(Collectors.toList());
    }

    /*
     * Для зарядних станцій: заряджається або ні
     */
    public void changeIsCharging(String name) {
        ChargerEntity charger = chargerRepository.findByName(name);
        charger.setIsCharging(!charger.getIsCharging());
        chargerRepository.save(charger);
    }

    /*
     * Для зарядних станцій: зламона або ні
     */
    public void changeIsBroken(String chargerName) {
        ChargerEntity charger = chargerRepository.findByName(chargerName);
        charger.setIsBroken(!charger.getIsBroken());
        chargerRepository.save(charger);
    }

    /*
     * Для власників електромобілів: отримання однієї зарядної станції
     */
    public ChargerDto getChargerById(Long chargerId) {
        return ChargerDto.fromCharger(findChargerById(chargerId));
    }

    /*
     * Для виробників зарядних станцій: видалення зарядної станції виробника
     */
    public void deleteChargerById(Long chargerUserId, Long chargerId) {
        ChargerUserEntity chargerUser = userService.findChargerUserById(chargerUserId);
        ChargerEntity charger = findChargerById(chargerId);
        chargerUser.getChargers().remove(charger);
        chargerRepository.delete(charger);
    }

    public int calculateDistanceInKilometer(double carLat, double carLng,
                                            double chargerLat, double chargerLng) {

        double latDistance = Math.toRadians(carLat - chargerLat);
        double lngDistance = Math.toRadians(carLng - chargerLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(carLat)) * Math.cos(Math.toRadians(chargerLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    public List<ChargerDto> getAllChargersForCar(Long latitude, Long longitude, Long percentOfBattery, String typeConnector) {
        if (
                latitude < -90.0 || latitude > 90.0 ||
                        longitude < -180.0 || longitude > 180.0 ||
                        percentOfBattery < 0 || percentOfBattery > 100
        ) {
            throw new BadRequestException("Something was wrong");
        }
        return chargerRepository
                .findAll()
                .stream()
                .filter(charger -> ChargerDto.isTypeConnector(charger, typeConnector))
                .filter(ChargerDto::isNoCharging)
                .filter(ChargerDto::isNoBroken)
                .filter(charger -> ChargerDto.isFast(charger, percentOfBattery))
                .map(ChargerDto::fromCharger)
                .collect(Collectors.toList());
    }
}
