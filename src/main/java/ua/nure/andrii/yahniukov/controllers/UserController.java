package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.IoT.CarDto;
import ua.nure.andrii.yahniukov.models.dto.forms.FormDescriptionDto;
import ua.nure.andrii.yahniukov.models.dto.forms.FormVinCodeDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.ChargerDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.StationDto;
import ua.nure.andrii.yahniukov.models.dto.users.UserDto;
import ua.nure.andrii.yahniukov.services.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ComplaintService complaintService;
    private final CarService carService;
    private final ChargerService chargerService;
    private final StationService stationService;

    @PostMapping("/{userId}/create/complaint/charger/{chargerId}")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Create a charger's complaint")
    public void createComplaintUserCharger(
            @PathVariable Long userId,
            @PathVariable Long chargerId,
            @RequestBody FormDescriptionDto complaint
    ) {
        complaintService.createComplaintUserCharger(userId, chargerId, complaint);
    }

    @PostMapping("/{userId}/create/complaint/station/{stationId}")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Create a station's complaint")
    public void createComplaintUserStation(
            @PathVariable Long userId,
            @PathVariable Long stationId,
            @RequestBody FormDescriptionDto complaint
    ) {
        complaintService.createComplaintUserStation(userId, stationId, complaint);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('get:user')")
    @ApiOperation(value = "View a user by id")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/get/car/{vinCode}")
    @PreAuthorize("hasAuthority('user:read')")
    @ApiOperation(value = "View a car by VIN code")
    public CarDto getCarByVinCode(@PathVariable String vinCode) {
        return carService.getCarByVinCode(vinCode);
    }

    @GetMapping("/{userId}/get/car/all")
    @PreAuthorize("hasAuthority('user:read')")
    @ApiOperation(value = "View list of user's cars by id")
    public List<CarDto> getAllUserCars(@PathVariable Long userId) {
        return carService.getAllUserCars(userId);
    }

    @PutMapping("/{userId}/add/car")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Add a car to user by user's id and car's VIN code")
    public void addCarToUserByVinCode(
            @PathVariable Long userId,
            @RequestBody FormVinCodeDto vinCode
    ) {
        carService.addCarToUserByVinCode(userId, vinCode);
    }

    @DeleteMapping("/{userId}/delete/car/{vinCode}")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Delete a user's car by user's id and car's VIN code")
    public void deleteCarFromUserByVinCode(
            @PathVariable Long userId,
            @PathVariable String vinCode
    ) {
        carService.deleteCarFromUserByVinCode(userId, vinCode);
    }

    @GetMapping("/get/charger/{chargerId}")
    @PreAuthorize("hasAuthority('user:read')")
    @ApiOperation(value = "View a charger by id")
    public ChargerDto getChargerById(@PathVariable Long chargerId) {
        return chargerService.getChargerById(chargerId);
    }

    @GetMapping("/get/station/{stationId}")
    @PreAuthorize("hasAuthority('user:read')")
    @ApiOperation(value = "View a station by id")
    public StationDto getStationById(@PathVariable Long stationId) {
        return stationService.getStationById(stationId);
    }
}