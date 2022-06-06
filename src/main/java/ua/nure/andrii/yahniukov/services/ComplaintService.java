package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.entities.*;
import ua.nure.andrii.yahniukov.repositories.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintService {
    private final ComplaintUserChargerRepository complaintUserChargerRepository;
    private final ComplaintUserStationRepository complaintUserStationRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;
    private final StationRepository stationRepository;

    public void createComplaintUserCharger(Long userId, Long chargerId, ComplaintUserChargerEntity complaint) {
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
                        .createdAt(new Date())
                        .build()
        );
    }

    public void createComplaintUserStation(Long userId, Long stationId, ComplaintUserStationEntity complaint) {
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
                        .createdAt(new Date())
                        .build()
        );
    }

    public List<ComplaintUserChargerEntity> getAllComplaintUserCharger() {
        return complaintUserChargerRepository.findAll();
    }

    public List<ComplaintUserStationEntity> getAllComplaintUserStation() {
        return complaintUserStationRepository.findAll();
    }

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
