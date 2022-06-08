package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormFreePlace;
import ua.nure.andrii.yahniukov.models.dto.forms.FormStationDto;
import ua.nure.andrii.yahniukov.services.StationService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;
    private final UserService userService;

    @PostMapping("/user/{stationUserId}/create")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Create a station by user's id")
    public ResponseEntity<String> createStation(
            @PathVariable Long stationUserId,
            @RequestBody FormStationDto station
    ) {
        try {
            stationService.createStation(stationUserId, station);
            return ResponseEntity.ok().body("Service station added successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{stationUserId}")
    @PreAuthorize("hasAuthority('station:read')")
    @ApiOperation(value = "View a station user by id")
    public ResponseEntity<?> getStationUserById(@PathVariable Long stationUserId) {
        try {
            return ResponseEntity.ok().body(userService.getStationUserById(stationUserId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{stationUserId}/get/all")
    @PreAuthorize("hasAuthority('station:read')")
    @ApiOperation(value = "View a list of station user's chargers")
    public ResponseEntity<?> getAllStationUserChargers(@PathVariable Long stationUserId) {
        try {
            return ResponseEntity.ok().body(stationService.getAllStationUserChargers(stationUserId));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/station/all")
    @ApiOperation(value = "View a list of stations")
    public ResponseEntity<?> getAllStations() {
        try {
            return ResponseEntity.ok().body(stationService.getAllStations());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/user/{stationUserId}/delete/{stationId}")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Delete a station by charger user id")
    public ResponseEntity<String> deleteStationById(
            @PathVariable Long stationUserId,
            @PathVariable Long stationId
    ) {
        try {
            stationService.deleteStationById(stationUserId, stationId);
            return ResponseEntity.ok().body("Service station deleted successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{stationId}/change/freePlace")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Change a free places for station by id")
    public void changeFreePlace(
            @PathVariable Long stationId,
            @RequestBody FormFreePlace freePlace
    ) {
        stationService.changeFreePlace(stationId, freePlace);
    }
}