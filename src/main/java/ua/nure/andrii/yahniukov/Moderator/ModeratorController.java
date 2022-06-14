package ua.nure.andrii.yahniukov.Moderator;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.ComplaintUserChargerService;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto.ComplaintUserChargerDto;
import ua.nure.andrii.yahniukov.ComplaintsUserStation.ComplaintUserStationService;
import ua.nure.andrii.yahniukov.ComplaintsUserStation.dto.ComplaintUserStationDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/moderator")
@RequiredArgsConstructor
public class ModeratorController {
    private final ComplaintUserChargerService complaintUserChargerService;
    private final ComplaintUserStationService complaintUserStationService;

    @GetMapping("/get/complaints/chargers/all")
    @PreAuthorize("hasAuthority('moderator:read')")
    @ApiOperation(value = "View a list of charger's complaints by  user")
    public List<ComplaintUserChargerDto> getAllComplaintUserCharger() {
        return complaintUserChargerService.getAll();
    }

    @GetMapping("/get/complaints/stations/all")
    @PreAuthorize("hasAuthority('moderator:read')")
    @ApiOperation(value = "View a list of station's complaints by user")
    public List<ComplaintUserStationDto> getAllComplaintUserStation() {
        return complaintUserStationService.getAll();
    }

    @DeleteMapping("/delete/complaints/chargers/{complaintId}")
    @PreAuthorize("hasAuthority('moderator:write')")
    @ApiOperation(value = "Delete a charger's complaint by id")
    public void deleteComplaintUserCharger(@PathVariable Long complaintId) {
        complaintUserChargerService.deleteById(complaintId);
    }

    @DeleteMapping("/delete/complaints/stations/{complaintId}")
    @PreAuthorize("hasAuthority('moderator:write')")
    @ApiOperation(value = "Delete a station's complaint by id")
    public void deleteComplaintUserStation(@PathVariable Long complaintId) {
        complaintUserStationService.deleteById(complaintId);
    }
}