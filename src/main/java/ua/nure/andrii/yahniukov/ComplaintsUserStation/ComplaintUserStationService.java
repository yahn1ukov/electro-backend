package ua.nure.andrii.yahniukov.ComplaintsUserStation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto.FormDescriptionDto;
import ua.nure.andrii.yahniukov.ComplaintsUserStation.dto.ComplaintUserStationDto;
import ua.nure.andrii.yahniukov.Station.StationEntity;
import ua.nure.andrii.yahniukov.Station.StationService;
import ua.nure.andrii.yahniukov.User.UserEntity;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintUserStationService {
    private final ComplaintUserStationRepository complaintUserStationRepository;
    private final UserService userService;
    private final StationService stationService;

    public ComplaintUserStationEntity findById(Long complaintId) {
        return complaintUserStationRepository
                .findById(complaintId)
                .orElseThrow(() -> new BadRequestException("Complaint with id " + complaintId + " not found"));
    }

    public void create(String email, String name, FormDescriptionDto complaint) {
        UserEntity user = userService.findByEmail(email);
        StationEntity station = stationService.findByName(name);
        complaintUserStationRepository.save(
                ComplaintUserStationEntity.builder()
                        .user(user)
                        .station(station)
                        .description(complaint.getDescription())
                        .build()
        );
    }

    public List<ComplaintUserStationDto> getAll() {
        return complaintUserStationRepository
                .findAll()
                .stream()
                .map(ComplaintUserStationDto::fromComplaint)
                .toList();
    }

    public void deleteById(Long complaintId) {
        ComplaintUserStationEntity complaint = findById(complaintId);
        UserEntity user = userService.findById(complaint.getUser().getId());
        StationEntity station = stationService.findById(complaint.getStation().getId());
        user.getStationComplaints().remove(complaint);
        station.getStationComplaints().remove(complaint);
        complaintUserStationRepository.delete(complaint);
    }
}
