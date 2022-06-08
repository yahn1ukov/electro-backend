package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.forms.FormFreePlace;
import ua.nure.andrii.yahniukov.models.dto.forms.FormStationDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.StationDto;
import ua.nure.andrii.yahniukov.models.dto.users.PartnerDto;
import ua.nure.andrii.yahniukov.services.StationService;
import ua.nure.andrii.yahniukov.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;
    private final UserService userService;

    @PostMapping("/user/{stationUserId}/create")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Create a station by user's id")
    public void createStation(
            @PathVariable Long stationUserId,
            @RequestBody FormStationDto station
    ) {
        stationService.createStation(stationUserId, station);
    }

    @GetMapping("/user/{stationUserId}")
    @PreAuthorize("hasAuthority('get:partner')")
    @ApiOperation(value = "View a station user by id")
    public PartnerDto getStationUserById(@PathVariable Long stationUserId) {
        return userService.getStationUserById(stationUserId);
    }

    @GetMapping("/user/{stationUserId}/get/all")
    @PreAuthorize("hasAuthority('station:read')")
    @ApiOperation(value = "View a list of station user's chargers")
    public List<StationDto> getAllStationUserChargers(@PathVariable Long stationUserId) {
        return stationService.getAllStationUserChargers(stationUserId);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "View a list of stations")
    public List<StationDto> getAllStations() {
        return stationService.getAllStations();
    }

    @DeleteMapping("/user/{stationUserId}/delete/{stationId}")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Delete a station by charger user id")
    public void deleteStationById(
            @PathVariable Long stationUserId,
            @PathVariable Long stationId
    ) {
        stationService.deleteStationById(stationUserId, stationId);
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