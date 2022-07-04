package ua.nure.andrii.yahniukov.moderator;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.complaint.ComplaintService;
import ua.nure.andrii.yahniukov.dto.complaint.ComplaintDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moderators")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('moderator:read', 'moderator:write')")
public class ModeratorController {
    private final ComplaintService complaintService;

    @GetMapping("/complaints/chargers")
    @ApiOperation(value = "View a list of charger's complaints by  user")
    public List<ComplaintDto> getAllComplaintUserCharger() {
        return complaintService.getAllComplaintsUserCharger();
    }

    @GetMapping("/complaints/stations")
    @ApiOperation(value = "View a list of station's complaints by user")
    public List<ComplaintDto> getAllComplaintUserStation() {
        return complaintService.getAllComplaintsUserStation();
    }

    @DeleteMapping("/complaints/chargers/{complaintId}/delete")
    @ApiOperation(value = "Delete a charger's complaint by id")
    public void deleteComplaintUserCharger(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserCharger(complaintId);
    }

    @DeleteMapping("/complaints/stations/{complaintId}/delete")
    @ApiOperation(value = "Delete a station's complaint by id")
    public void deleteComplaintUserStation(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserStation(complaintId);
    }
}