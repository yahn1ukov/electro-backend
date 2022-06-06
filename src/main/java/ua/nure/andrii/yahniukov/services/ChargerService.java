package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.entities.ChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.TypeConnectorsEntity;
import ua.nure.andrii.yahniukov.repositories.ChargerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerService {
    private final ChargerRepository chargerRepository;

    public void createCharger(ChargerEntity charger, TypeConnectorsEntity typeConnectors) {
        chargerRepository.save(
                ChargerEntity.builder()
                        .name(charger.getName())
                        .country(charger.getCountry())
                        .city(charger.getCity())
                        .street(charger.getStreet())
                        .zipCode(charger.getZipCode())
                        .latitude(charger.getLatitude())
                        .longitude(charger.getLongitude())
                        .isCharging(false)
                        .isBroken(false)
                        .isFast(charger.getIsFast())
                        .isPay(charger.getIsPay())
                        .priceOfPerHour(charger.getPriceOfPerHour())
                        .company(charger.getCompany())
                        .phoneNumber(charger.getPhoneNumber())
                        .webSite(charger.getWebSite())
                        .timeFrom(charger.getTimeFrom())
                        .timeTo(charger.getTimeTo())
                        .typeConnectors(new ArrayList<>(Collections.singletonList(typeConnectors)))
                        .chargerComplaints(new ArrayList<>(Collections.emptyList()))
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build()
        );
    }

    public List<ChargerEntity> getAllCharger() {
        return chargerRepository.findAll();
    }

    public ChargerEntity getChargerById(Long id) {
        return chargerRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Charger with id: " + id + " not found"));
    }

    public void deleteChargerById(Long id) {
        if (!chargerRepository.findById(id).isPresent()) {
            throw new BadRequestException("Charger with id: " + id + " not found");
        }
        chargerRepository.deleteById(id);
    }
}
