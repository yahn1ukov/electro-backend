package ua.nure.andrii.yahniukov.Charger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.Charger.dto.ChargerDto;
import ua.nure.andrii.yahniukov.Charger.dto.FormChargerDto;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerService {
    private final ChargerRepository chargerRepository;
    private final ChargerUserService chargerUserService;

    public ChargerEntity findByCode(String code) {
        return chargerRepository
                .findByCode(code)
                .orElseThrow(() -> new BadRequestException("Charger with code " + code + " not found"));
    }

    public ChargerEntity findById(Long id) {
        return chargerRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Charger with id " + id + " not found"));
    }

    public void create(String email, FormChargerDto charger) {
        if (chargerRepository.existsByCode(charger.getCode())) {
            throw new BadRequestException("Charger with code " + charger.getCode() + " already exists");
        }
        ChargerUserEntity chargerUser = chargerUserService.findByEmail(email);
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
    }

    public List<ChargerDto> getAllForChargerUser(String email) {
        ChargerUserEntity chargerUser = chargerUserService.findByEmail(email);
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

    public void changeIsCharging(String code) {
        ChargerEntity charger = findByCode(code);
        charger.setIsCharging(!charger.getIsCharging());
        chargerRepository.save(charger);
    }

    public void changeIsBroken(String code) {
        ChargerEntity charger = findByCode(code);
        charger.setIsBroken(!charger.getIsBroken());
        chargerRepository.save(charger);
    }

    public ChargerDto getByCode(String code) {
        return ChargerDto.fromCharger(findByCode(code));
    }

    public void deleteByCode(String email, String code) {
        ChargerUserEntity chargerUser = chargerUserService.findByEmail(email);
        ChargerEntity charger = findByCode(code);
        chargerUser.getChargers().remove(charger);
        chargerRepository.delete(charger);
    }

    public List<ChargerDto> getAllForCar(Long latitude, Long longitude, Long percentOfBattery, String typeConnector, Integer radius) {
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
                .filter(charger -> ChargerDto.isRadius(charger, latitude, longitude, radius))
                .filter(charger -> ChargerDto.isTypeConnector(charger, typeConnector))
                .filter(ChargerDto::isNoCharging)
                .filter(ChargerDto::isNoBroken)
                .filter(charger -> ChargerDto.isFast(charger, percentOfBattery))
                .map(ChargerDto::fromCharger)
                .collect(Collectors.toList());
    }
}