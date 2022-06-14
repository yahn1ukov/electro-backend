package ua.nure.andrii.yahniukov.ComplaintsUserCharger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.Charger.ChargerEntity;
import ua.nure.andrii.yahniukov.Charger.ChargerService;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto.ComplaintUserChargerDto;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto.FormDescriptionDto;
import ua.nure.andrii.yahniukov.User.UserEntity;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintUserChargerService {
    private final ComplaintUserChargerRepository complaintUserChargerRepository;
    private final UserService userService;
    private final ChargerService chargerService;

    public ComplaintUserChargerEntity findById(Long complaintId) {
        return complaintUserChargerRepository
                .findById(complaintId)
                .orElseThrow(() -> new BadRequestException("Complaint with id " + complaintId + " not found"));
    }

    public void create(String userEmail, String chargerCode, FormDescriptionDto complaint) {
        UserEntity user = userService.findByEmail(userEmail);
        ChargerEntity charger = chargerService.findByCode(chargerCode);
        complaintUserChargerRepository.save(
                ComplaintUserChargerEntity.builder()
                        .user(user)
                        .charger(charger)
                        .description(complaint.getDescription())
                        .build()
        );
    }

    public List<ComplaintUserChargerDto> getAll() {
        return complaintUserChargerRepository
                .findAll()
                .stream()
                .map(ComplaintUserChargerDto::fromComplaint)
                .toList();
    }

    public void deleteById(Long complaintId) {
        ComplaintUserChargerEntity complaint = findById(complaintId);
        UserEntity user = userService.findById(complaint.getUser().getId());
        ChargerEntity charger = chargerService.findById(complaint.getCharger().getId());
        user.getChargerComplaints().remove(complaint);
        charger.getChargerComplaints().remove(complaint);
        complaintUserChargerRepository.delete(complaint);
    }
}