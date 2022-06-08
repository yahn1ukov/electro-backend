package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.services.ComplaintService;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ComplaintService complaintService;

    @GetMapping("/get/complaint/charger/all")
    @PreAuthorize("hasAuthority('moderator:read')")
    @ApiOperation(value = "View a list of charger's complaints by  user")
    public ResponseEntity<?> getAllComplaintUserCharger() {
        try {
            return ResponseEntity.ok().body(complaintService.getAllComplaintUserCharger());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/complaint/station/all")
    @PreAuthorize("hasAuthority('moderator:read')")
    @ApiOperation(value = "View a list of station's complaints by user")
    public ResponseEntity<?> getAllComplaintUserStation() {
        try {
            return ResponseEntity.ok().body(complaintService.getAllComplaintUserStation());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/complaint/charger/{complaintId}")
    @PreAuthorize("hasAuthority('moderator:write')")
    @ApiOperation(value = "Delete a charger's complaint by id")
    public ResponseEntity<String> deleteComplaintUserCharger(@PathVariable Long complaintId) {
        try {
            complaintService.deleteComplaintUserCharger(complaintId);
            return ResponseEntity.ok().body("Complaint successfully deleted");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("delete/complaint/station/{complaintId}")
    @PreAuthorize("hasAuthority('moderator:write')")
    @ApiOperation(value = "Delete a station's complaint by id")
    public ResponseEntity<String> deleteComplaintUserStation(@PathVariable Long complaintId) {
        try {
            complaintService.deleteComplaintUserStation(complaintId);
            return ResponseEntity.ok().body("Complaint successfully deleted");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}