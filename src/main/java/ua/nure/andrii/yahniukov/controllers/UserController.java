package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.CarDto;
import ua.nure.andrii.yahniukov.models.dto.ComplaintDto;
import ua.nure.andrii.yahniukov.models.dto.VinCodeDto;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterUserDto;
import ua.nure.andrii.yahniukov.services.CarService;
import ua.nure.andrii.yahniukov.services.ComplaintService;
import ua.nure.andrii.yahniukov.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ComplaintService complaintService;
    private final CarService carService;

    @PostMapping("/create/user")
    @ApiOperation(value = "Create a user")
    public void createUser(@RequestBody RegisterUserDto user) {
        userService.createUser(user);
    }

    @PostMapping("/{userId}/create/complaint/charger/{chargerId}")
    @ApiOperation(value = "Create a charger's complaint")
    public void createComplaintUserCharger(
            @PathVariable Long userId,
            @PathVariable Long chargerId,
            @RequestBody ComplaintDto complaint
    ) {
        complaintService.createComplaintUserCharger(userId, chargerId, complaint);
    }

    @PostMapping("/{userId}/create/complaint/station/{stationId}")
    @ApiOperation(value = "Create a station's complaint")
    public void createComplaintUserStation(
            @PathVariable Long userId,
            @PathVariable Long stationId,
            @RequestBody ComplaintDto complaint
    ) {
        complaintService.createComplaintUserStation(userId, stationId, complaint);
    }

    @GetMapping("/get/car/{vinCode}")
    @ApiOperation(value = "View a car by VIN code")
    public CarDto getCarByVinCode(@PathVariable String vinCode) {
        return carService.getCarByVinCode(vinCode);
    }

    @GetMapping("/{userId}/get/car/all")
    @ApiOperation(value = "View list of user's cars by id")
    public List<CarDto> getAllUserCars(@PathVariable Long userId) {
        return carService.getAllUserCars(userId);
    }

    @PutMapping("/{userId}/add/car")
    @ApiOperation(value = "Add a car to user by user's id and car's VIN code")
    public void addCarToUserByVinCode(
            @PathVariable Long userId,
            @RequestBody VinCodeDto vinCode
    ) {
        carService.addCarToUserByVinCode(userId, vinCode);
    }

    @DeleteMapping("/{userId}/delete/car/{vinCode}")
    @ApiOperation(value = "Delete a user's car by user's id and car's VIN code")
    public void deleteCarFromUserByVinCode(
            @PathVariable Long userId,
            @PathVariable String vinCode
    ) {
        carService.deleteCarFromUserByVinCode(userId, vinCode);
    }
}
