package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.ChargerDto;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;
import ua.nure.andrii.yahniukov.repositories.ChargerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerService {
    private final ChargerRepository chargerRepository;

    public void createCharger(ChargerDto charger) {
        if (chargerRepository.existsByName(charger.getName())) {
            throw new BadRequestException("Charger with name: " + charger.getName() + " already exists");
        }
        chargerRepository.save(
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
                        .company(charger.getCompany())
                        .typeConnector(charger.getTypeConnector())
                        .phoneNumber(charger.getPhoneNumber())
                        .webSite(charger.getWebSite())
                        .timeFrom(charger.getTimeFrom())
                        .timeTo(charger.getTimeTo())
                        .build()
        );
    }

    public List<ChargerDto> getAllCharger() {
        return chargerRepository
                .findAll()
                .stream()
                .map(ChargerDto::fromCharger)
                .collect(Collectors.toList());
    }

    public ChargerDto getChargerById(Long id) {
        return ChargerDto.fromCharger(
                chargerRepository
                        .findById(id)
                        .orElseThrow(() -> new BadRequestException("Charger with id: " + id + " not found"))
        );
    }

    public void deleteChargerById(Long id) {
        if (!chargerRepository.existsById(id)) {
            throw new BadRequestException("Charger with id: " + id + " not found");
        }
        chargerRepository.deleteById(id);
    }
}
