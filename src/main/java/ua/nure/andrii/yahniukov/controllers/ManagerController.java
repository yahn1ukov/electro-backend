package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.entities.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.services.ComplaintService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ComplaintService complaintService;

    @GetMapping("/get/complaint/charger/all")
    @ApiOperation(value = "View list of charger's complaints")
    public List<ComplaintUserChargerEntity> getAllComplaintUserCharger() {
        return complaintService.getAllComplaintUserCharger();
    }

    @GetMapping("/get/complaint/station/all")
    @ApiOperation(value = "View list of station's complaints")
    public List<ComplaintUserStationEntity> getAllComplaintUserStation() {
        return complaintService.getAllComplaintUserStation();
    }

    @DeleteMapping("/delete/complaint/charger/{complaintId}")
    @ApiOperation(value = "Delete a complaint of charger by id")
    public void deleteComplaintUserCharger(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserCharger(complaintId);
    }

    @DeleteMapping("/delete/complaint/station/{complaintId}")
    @ApiOperation(value = "Delete a complaint of station by id")
    public void deleteComplaintUserStation(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserStation(complaintId);
    }
}
