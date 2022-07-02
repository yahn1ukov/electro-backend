package ua.nure.andrii.yahniukov.charger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.dto.charger.ChargerDto;
import ua.nure.andrii.yahniukov.dto.charger.FormChargerDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.exception.charger.ChargerAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.charger.ChargerNotFoundException;
import ua.nure.andrii.yahniukov.iot.CarEntity;
import ua.nure.andrii.yahniukov.iot.CarService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerService {
    private final ChargerRepository chargerRepository;
    private final ChargerUserService chargerUserService;
    private final CarService carService;

    public ChargerEntity findById(Long id) {
        return chargerRepository
                .findById(id)
                .orElseThrow(ChargerNotFoundException::new);
    }

    public SuccessMessageDto create(Long userId, FormChargerDto charger) {
        if (chargerRepository.existsByCode(charger.getCode())) {
            throw new ChargerAlreadyExistsException();
        }
        ChargerUserEntity chargerUser = chargerUserService.findById(userId);
        ChargerEntity createdCharger = chargerRepository.save(
                ChargerEntity.builder()
                        .code(charger.getCode())
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
        return SuccessMessageDto.builder().message("Charger station successfully created").build();
    }

    public List<ChargerDto> getAllForChargerUser(Long userId) {
        ChargerUserEntity chargerUser = chargerUserService.findById(userId);
        return chargerRepository
                .findAllByOwner(chargerUser)
                .stream()
                .map(ChargerDto::fromCharger)
                .toList();
    }

    public List<ChargerDto> getAll() {
        return chargerRepository
                .findAll()
                .stream()
                .map(ChargerDto::fromCharger)
                .toList();
    }

    public void changeIsCharging(Long id) {
        ChargerEntity charger = findById(id);
        charger.setIsCharging(!charger.getIsCharging());
        chargerRepository.save(charger);
    }

    public void changeIsBroken(Long id) {
        ChargerEntity charger = findById(id);
        charger.setIsBroken(!charger.getIsBroken());
        chargerRepository.save(charger);
    }

    public ChargerDto get(Long id) {
        return ChargerDto.fromCharger(findById(id));
    }

    public void delete(Long userId, Long chargerId) {
        ChargerUserEntity chargerUser = chargerUserService.findById(userId);
        ChargerEntity charger = findById(chargerId);
        chargerUser.getChargers().remove(charger);
        chargerRepository.deleteById(chargerId);
    }

    public List<ChargerDto> getAllForCar(String carVinCode) {
        CarEntity car = carService.findByVinCode(carVinCode);
        return chargerRepository
                .findAll()
                .stream()
                .filter(charger -> charger.getTypeConnector().equals(car.getTypeConnector()))
                .filter(charger -> !charger.getIsCharging())
                .filter(charger -> !charger.getIsBroken())
                .filter(charger -> car.getPercentageOfCharge() >= 0.0 &&
                        car.getPercentageOfCharge() <= 20.0 &&
                        carService.calculateRadius(
                                car.getLatitude(),
                                car.getLongitude(),
                                charger.getLatitude(),
                                charger.getLongitude()
                        ) <= 35 ?
                        charger.getIsFast() :
                        car.getPercentageOfCharge() > 20.0 &&
                                car.getPercentageOfCharge() <= 50.0 &&
                                carService.calculateRadius(
                                        car.getLatitude(),
                                        car.getLongitude(),
                                        charger.getLatitude(),
                                        charger.getLongitude()
                                ) <= 60 ?
                                !charger.getIsFast() :
                                null

                )
                .map(ChargerDto::fromCharger)
                .toList();
    }
}