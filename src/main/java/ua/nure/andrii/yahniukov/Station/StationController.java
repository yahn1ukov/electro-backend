package ua.nure.andrii.yahniukov.Station;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.Station.dto.FormFreePlaceDto;
import ua.nure.andrii.yahniukov.Station.dto.FormStationDto;
import ua.nure.andrii.yahniukov.Station.dto.StationDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @PostMapping("/create/by/station/users/{email}")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Create a station by user's email")
    public void createStation(
            @PathVariable String email,
            @RequestBody FormStationDto station
    ) {
        stationService.create(email, station);
    }

    @GetMapping("/get/all/for/station/users/{email}")
    @PreAuthorize("hasAuthority('station:read')")
    @ApiOperation(value = "View a list of station user's chargers")
    public List<StationDto> getAllForStationUser(@PathVariable String email) {
        return stationService.getAllForStationUser(email);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "View a list of stations")
    public List<StationDto> getAllStations() {
        return stationService.getAll();
    }

    @GetMapping("/{name}/get")
    @ApiOperation(value = "View a charger")
    public StationDto getByName(@PathVariable String name) {
        return stationService.getByName(name);
    }

    @DeleteMapping("/{name}/delete/by/station/users/{email}")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Delete a station by charger user email")
    public void deleteByName(
            @PathVariable String email,
            @PathVariable String name
    ) {
        stationService.deleteByName(email, name);
    }

    @PatchMapping("/{name}/change/free-place")
    @PreAuthorize("hasAuthority('station:write')")
    @ApiOperation(value = "Change a free places for station by name")
    public void changeFreePlace(
            @PathVariable String name,
            @RequestBody FormFreePlaceDto freePlace
    ) {
        stationService.changeFreePlace(name, freePlace);
    }

    @GetMapping("/{latitude}/{longitude}/{name}/{model}/{radius}")
    @ApiOperation(value = "Get a list of stations by geolocation of car and some data")
    public List<StationDto> getAllStationsForCar(
            @PathVariable Long latitude,
            @PathVariable Long longitude,
            @PathVariable String name,
            @PathVariable String model,
            @PathVariable Integer radius
    ) {
        return stationService.getAllForCar(latitude, longitude, name, model, radius);
    }
}