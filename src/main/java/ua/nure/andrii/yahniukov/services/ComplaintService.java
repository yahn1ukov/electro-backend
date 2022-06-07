package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.complaints.ComplaintDto;
import ua.nure.andrii.yahniukov.models.dto.forms.FormDescriptionDto;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.complaints.ComplaintUserChargerRepository;
import ua.nure.andrii.yahniukov.repositories.complaints.ComplaintUserStationRepository;
import ua.nure.andrii.yahniukov.repositories.maintenances.ChargerRepository;
import ua.nure.andrii.yahniukov.repositories.maintenances.StationRepository;
import ua.nure.andrii.yahniukov.repositories.users.UserRepository;

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
    private final UserService userService;

    public ChargerEntity findChargerById(Long chargerId) {
        return chargerRepository
                .findById(chargerId)
                .orElseThrow(() -> new BadRequestException("Charger with id: " + chargerId + " not found"));
    }

    public StationEntity findStationById(Long stationId) {
        return stationRepository
                .findById(stationId)
                .orElseThrow(() -> new BadRequestException("Station with id: " + stationId + " not found"));
    }

    public ComplaintUserChargerEntity findComplaintUserChargerById(Long complaintId) {
        return complaintUserChargerRepository
                .findById(complaintId)
                .orElseThrow(() -> new BadRequestException("Complaint with id: " + complaintId + " not found"));
    }

    public ComplaintUserStationEntity findComplaintUserStationById(Long complaintId) {
        return complaintUserStationRepository
                .findById(complaintId)
                .orElseThrow(() -> new BadRequestException("Complaint with id: " + complaintId + " not found"));
    }

    /*
     * Для власників електромобілів: подання скарги на зарядну станцію
     */
    public void createComplaintUserCharger(Long userId, Long chargerId, FormDescriptionDto complaint) {
        UserEntity user = userService.findUserById(userId);
        ChargerEntity charger = findChargerById(chargerId);
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
    public void createComplaintUserStation(Long userId, Long stationId, FormDescriptionDto complaint) {
        UserEntity user = userService.findUserById(userId);
        StationEntity station = findStationById(stationId);
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
    public List<ComplaintDto> getAllComplaintUserCharger() {
        return complaintUserChargerRepository
                .findAll()
                .stream()
                .map(ComplaintDto::fromComplaint)
                .collect(Collectors.toList());
    }

    /*
     * Для модерації: перегляд усіх скарг на СТО
     */
    public List<ComplaintDto> getAllComplaintUserStation() {
        return complaintUserStationRepository
                .findAll()
                .stream()
                .map(ComplaintDto::fromComplaint)
                .collect(Collectors.toList());
    }

    /*
     * Для модерації: видалити скаргу на зарядну станцію
     */
    public void deleteComplaintUserCharger(Long complaintId) {
        ComplaintUserChargerEntity complaint = findComplaintUserChargerById(complaintId);
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
        ComplaintUserStationEntity complaint = findComplaintUserStationById(complaintId);
        UserEntity user = userRepository.getById(complaint.getUser().getId());
        StationEntity station = stationRepository.getById(complaint.getStation().getId());
        user.getStationComplaints().remove(complaint);
        station.getStationComplaints().remove(complaint);
        complaintUserStationRepository.delete(complaint);
    }
}