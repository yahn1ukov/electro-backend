package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.entities.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.services.ComplaintService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaint")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping("/create/user/{userId}/charger/{chargerId}")
    @ApiOperation(value = "Create a complaint of charger by user")
    public void createComplaintUserCharger(
            @PathVariable Long userId,
            @PathVariable Long chargerId,
            @RequestBody ComplaintUserChargerEntity complaint
    ) {
        complaintService.createComplaintUserCharger(userId, chargerId, complaint);
    }

    @PostMapping("/create/user/{userId}/station/{stationId}")
    @ApiOperation(value = "Create a complaint of station by user")
    public void createComplaintUserStation(
            @PathVariable Long userId,
            @PathVariable Long stationId,
            @RequestBody ComplaintUserStationEntity complaint
    ) {
        complaintService.createComplaintUserStation(userId, stationId, complaint);
    }

    @GetMapping("/get/charger/all")
    @ApiOperation(value = "View list of charger's complaints")
    public List<ComplaintUserChargerEntity> getAllComplaintUserCharger() {
        return complaintService.getAllComplaintUserCharger();
    }

    @GetMapping("/get/station/all")
    @ApiOperation(value = "View list of station's complaints")
    public List<ComplaintUserStationEntity> getAllComplaintUserStation() {
        return complaintService.getAllComplaintUserStation();
    }

    @DeleteMapping("/delete/charger/{complaintId}")
    @ApiOperation(value = "Delete a complaint of charger by id")
    public void deleteComplaintUserCharger(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserCharger(complaintId);
    }

    @DeleteMapping("/delete/station/{complaintId}")
    @ApiOperation(value = "Delete a complaint of station by id")
    public void deleteComplaintUserStation(@PathVariable Long complaintId) {
        complaintService.deleteComplaintUserStation(complaintId);
    }
}
