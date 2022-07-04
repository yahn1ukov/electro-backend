package ua.nure.andrii.yahniukov.station;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
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

    @PostMapping("/users/current/create")
    @ApiOperation(value = "Create a station by station user's id")
    public SuccessMessageDto create(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId, @RequestBody FormStationDto station) {
        return stationService.create(userId, station);
    }

    @GetMapping("/users/current")
    @ApiOperation(value = "View a list of station user's chargers")
    public List<StationDto> getAllForStationUser(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId) {
        return stationService.getAllForStationUser(userId);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a list of stations")
    public List<StationDto> getAllStations() {
        return stationService.getAll();
    }

    @GetMapping("/{stationId}")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a station by id")
    public StationDto getByName(@PathVariable Long stationId) {
        return stationService.get(stationId);
    }

    @DeleteMapping("/{stationId}/users/current/delete")
    @ApiOperation(value = "Delete a station by station user's id")
    public void delete(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId, @PathVariable Long stationId) {
        stationService.delete(userId, stationId);
    }

    @PatchMapping("/{stationName}/free-place")
    @ApiOperation(value = "Change a free places for station by id")
    public SuccessMessageDto changeFreePlace(@PathVariable String stationName, @RequestBody FormFreePlaceDto freePlace) {
        return stationService.changeFreePlace(stationName, freePlace);
    }
}