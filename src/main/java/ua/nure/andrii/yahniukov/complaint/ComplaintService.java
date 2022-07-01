package ua.nure.andrii.yahniukov.complaint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.charger.ChargerEntity;
import ua.nure.andrii.yahniukov.charger.ChargerService;
import ua.nure.andrii.yahniukov.dto.complaint.ComplaintDto;
import ua.nure.andrii.yahniukov.dto.complaint.DescriptionDto;
import ua.nure.andrii.yahniukov.exceptions.complaint.ComplaintNotFoundException;
import ua.nure.andrii.yahniukov.station.StationEntity;
import ua.nure.andrii.yahniukov.station.StationService;
import ua.nure.andrii.yahniukov.user.UserEntity;
import ua.nure.andrii.yahniukov.user.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintService {
    private final ComplaintUserChargerRepository complaintUserChargerRepository;
    private final ComplaintUserStationRepository complaintUserStationRepository;
    private final UserService userService;
    private final ChargerService chargerService;
    private final StationService stationService;

    public ComplaintUserChargerEntity findComplaintUserChargerById(Long id) {
        return complaintUserChargerRepository
                .findById(id)
                .orElseThrow(ComplaintNotFoundException::new);
    }

    public ComplaintUserStationEntity findComplaintUserStationById(Long id) {
        return complaintUserStationRepository
                .findById(id)
                .orElseThrow(ComplaintNotFoundException::new);
    }

    public void createComplaintUserCharger(Long userId, Long chargerId, DescriptionDto description) {
        UserEntity user = userService.findById(userId);
        ChargerEntity charger = chargerService.findById(chargerId);
        complaintUserChargerRepository.save(
                ComplaintUserChargerEntity.builder()
                        .user(user)
                        .charger(charger)
                        .description(description.getDescription())
                        .build()
        );
    }

    public void createComplaintUserStation(Long userId, Long stationId, DescriptionDto description) {
        UserEntity user = userService.findById(userId);
        StationEntity station = stationService.findById(stationId);
        complaintUserStationRepository.save(
                ComplaintUserStationEntity.builder()
                        .user(user)
                        .station(station)
                        .description(description.getDescription())
                        .build()
        );
    }

    public List<ComplaintDto> getAllComplaintsUserCharger() {
        return complaintUserChargerRepository
                .findAll()
                .stream()
                .map(ComplaintDto::fromComplaint)
                .toList();
    }

    public List<ComplaintDto> getAllComplaintsUserStation() {
        return complaintUserStationRepository
                .findAll()
                .stream()
                .map(ComplaintDto::fromComplaint)
                .toList();
    }

    public void deleteComplaintUserCharger(Long id) {
        ComplaintUserChargerEntity complaint = findComplaintUserChargerById(id);
        UserEntity user = userService.findById(complaint.getUser().getId());
        ChargerEntity charger = chargerService.findById(complaint.getCharger().getId());
        user.getChargerComplaints().remove(complaint);
        charger.getChargerComplaints().remove(complaint);
        complaintUserChargerRepository.deleteById(id);
    }

    public void deleteComplaintUserStation(Long id) {
        ComplaintUserStationEntity complaint = findComplaintUserStationById(id);
        UserEntity user = userService.findById(complaint.getUser().getId());
        StationEntity station = stationService.findById(complaint.getStation().getId());
        user.getStationComplaints().remove(complaint);
        station.getStationComplaints().remove(complaint);
        complaintUserStationRepository.deleteById(id);
    }
}