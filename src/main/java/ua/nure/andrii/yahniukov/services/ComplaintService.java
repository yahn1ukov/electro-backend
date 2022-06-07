package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.ComplaintDto;
import ua.nure.andrii.yahniukov.models.dto.ComplaintUserChargerDto;
import ua.nure.andrii.yahniukov.models.dto.ComplaintUserStationDto;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintService {
    private final ComplaintUserChargerRepository complaintUserChargerRepository;
    private final ComplaintUserStationRepository complaintUserStationRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;
    private final StationRepository stationRepository;

    /*
     * Для власників електромобілів: подання скарги на зарядну станцію
     */
    public void createComplaintUserCharger(Long userId, Long chargerId, ComplaintDto complaint) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        ChargerEntity charger = chargerRepository
                .findById(chargerId)
                .orElseThrow(() -> new BadRequestException("Charger with id: " + chargerId + " not found"));

        complaintUserChargerRepository.save(
                ComplaintUserChargerEntity.builder()
                        .user(user)
                        .charger(charger)
                        .description(complaint.getDescription())
                        .build()
        );
    }

    /*
     * Для власників електромобілів: подання скарги на СТО
     */
    public void createComplaintUserStation(Long userId, Long stationId, ComplaintDto complaint) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));
        StationEntity station = stationRepository
                .findById(stationId)
                .orElseThrow(() -> new BadRequestException("Station with id: " + stationId + " not found"));

        complaintUserStationRepository.save(
                ComplaintUserStationEntity.builder()
                        .user(user)
                        .station(station)
                        .description(complaint.getDescription())
                        .build()
        );
    }

    /*
     * Для модерації: перегляд усіх скарг на зарядну станцію
     */
    public List<ComplaintUserChargerDto> getAllComplaintUserCharger() {
        return complaintUserChargerRepository
                .findAll()
                .stream()
                .map(ComplaintUserChargerDto::fromComplaintUserCharger)
                .collect(Collectors.toList());
    }

    /*
     * Для модерації: перегляд усіх скарг на СТО
     */
    public List<ComplaintUserStationDto> getAllComplaintUserStation() {
        return complaintUserStationRepository
                .findAll()
                .stream()
                .map(ComplaintUserStationDto::fromComplaintUserStation)
                .collect(Collectors.toList());
    }

    /*
     * Для модерації: видалити скаргу на зарядну станцію
     */
    public void deleteComplaintUserCharger(Long complaintId) {
        ComplaintUserChargerEntity complaint = complaintUserChargerRepository
                .findById(complaintId)
                .orElseThrow(() -> new BadRequestException("Complaint with id: " + complaintId + " not found"));

        UserEntity user = userRepository.getById(complaint.getUser().getId());
        ChargerEntity charger = chargerRepository.getById(complaint.getCharger().getId());

        user.getChargerComplaints().remove(complaint);
        charger.getChargerComplaints().remove(complaint);

        complaintUserChargerRepository.delete(complaint);
    }

    /*
     * Для модерації: видалити скаргу на СТО
     */
    public void deleteComplaintUserStation(Long complaintId) {
        ComplaintUserStationEntity complaint = complaintUserStationRepository
                .findById(complaintId)
                .orElseThrow(() -> new BadRequestException("Complaint with id: " + complaintId + " not found"));

        UserEntity user = userRepository.getById(complaint.getUser().getId());
        StationEntity station = stationRepository.getById(complaint.getStation().getId());

        user.getStationComplaints().remove(complaint);
        station.getStationComplaints().remove(complaint);

        complaintUserStationRepository.delete(complaint);
    }
}