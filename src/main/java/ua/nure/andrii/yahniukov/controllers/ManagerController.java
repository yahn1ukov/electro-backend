package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.ComplaintUserChargerDto;
import ua.nure.andrii.yahniukov.models.dto.ComplaintUserStationDto;
import ua.nure.andrii.yahniukov.services.ComplaintService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ComplaintService complaintService;

    @GetMapping("/get/complaint/charger/all")
    @ApiOperation(value = "View a list of charger's complaints by  user")
    public List<ComplaintUserChargerDto> getAllComplaintUserCharger() {
        return complaintService.getAllComplaintUserCharger();
    }

    @GetMapping("/get/complaint/station/all")
    @ApiOperation(value = "View a list of station's complaints by user")
    public List<ComplaintUserStationDto> getAllComplaintUserStation() {
        return complaintService.getAllComplaintUserStation();
    }

    @DeleteMapping("/delete/complaint/charger/{complaintId}")
    @ApiOperation(value = "Delete a charger's complaint by id")
    public void deleteComplaintUserCharger(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserCharger(complaintId);
    }

    @DeleteMapping("delete/complaint/station/{complaintId}")
    @ApiOperation(value = "Delete a station's complaint by id")
    public void deleteComplaintUserStation(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserStation(complaintId);
    }
}
