package ua.nure.andrii.yahniukov.station;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.station.FormFreePlaceDto;
import ua.nure.andrii.yahniukov.dto.station.FormStationDto;
import ua.nure.andrii.yahniukov.dto.station.StationDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('station:read', 'station:write')")
public class StationController {
    private final StationService stationService;

    @PostMapping("/users/{userId}/create")
    @ApiOperation(value = "Create a station by station user's id")
    public void create(@PathVariable Long userId, @RequestBody FormStationDto station) {
        stationService.create(userId, station);
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "View a list of station user's chargers")
    public List<StationDto> getAllForStationUser(@PathVariable Long userId) {
        return stationService.getAllForStationUser(userId);
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a list of stations")
    public List<StationDto> getAllStations() {
        return stationService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('station:read', 'station:write', 'user:read', 'user:write')")
    @ApiOperation(value = "View a station by id")
    public StationDto getByName(@PathVariable Long id) {
        return stationService.get(id);
    }

    @DeleteMapping("/{stationId}/users/{userId}/delete")
    @ApiOperation(value = "Delete a station by station user's id")
    public void delete(@PathVariable Long userId, @PathVariable Long stationId) {
        stationService.delete(userId, stationId);
    }

    @PatchMapping("/{id}/free-place")
    @ApiOperation(value = "Change a free places for station by id")
    public void changeFreePlace(@PathVariable Long id, @RequestBody FormFreePlaceDto freePlace) {
        stationService.changeFreePlace(id, freePlace);
    }
}