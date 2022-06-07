package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.helpers.DescriptionDto;
import ua.nure.andrii.yahniukov.models.dto.helpers.VinCodeDto;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterUserDto;
import ua.nure.andrii.yahniukov.services.CarService;
import ua.nure.andrii.yahniukov.services.ComplaintService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ComplaintService complaintService;
    private final CarService carService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a user")
    public ResponseEntity<String> createUser(@RequestBody RegisterUserDto user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok().body("User successfully created");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{userId}/create/complaint/charger/{chargerId}")
    @ApiOperation(value = "Create a charger's complaint")
    public ResponseEntity<String> createComplaintUserCharger(
            @PathVariable Long userId,
            @PathVariable Long chargerId,
            @RequestBody DescriptionDto complaint
    ) {
        try {
            complaintService.createComplaintUserCharger(userId, chargerId, complaint);
            return ResponseEntity.ok().body("Charging station complaint successfully sent");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{userId}/create/complaint/station/{stationId}")
    @ApiOperation(value = "Create a station's complaint")
    public ResponseEntity<String> createComplaintUserStation(
            @PathVariable Long userId,
            @PathVariable Long stationId,
            @RequestBody DescriptionDto complaint
    ) {
        try {
            complaintService.createComplaintUserStation(userId, stationId, complaint);
            return ResponseEntity.ok().body("Service station complaint sent successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "View a user by id")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(userId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/charger/{chargerUserId}")
    @ApiOperation(value = "View a charger user by id")
    public ResponseEntity<?> getChargerUserById(@PathVariable Long chargerUserId) {
        try {
            return ResponseEntity.ok().body(userService.getChargerUserById(chargerUserId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/station/{stationUserId}")
    @ApiOperation(value = "View a station user by id")
    public ResponseEntity<?> getStationUserById(@PathVariable Long stationUserId) {
        try {
            return ResponseEntity.ok().body(userService.getStationUserById(stationUserId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/car/{vinCode}")
    @ApiOperation(value = "View a car by VIN code")
    public ResponseEntity<?> getCarByVinCode(@PathVariable String vinCode) {
        try {
            return ResponseEntity.ok().body(carService.getCarByVinCode(vinCode));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{userId}/get/car/all")
    @ApiOperation(value = "View list of user's cars by id")
    public ResponseEntity<?> getAllUserCars(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok().body(carService.getAllUserCars(userId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{userId}/add/car")
    @ApiOperation(value = "Add a car to user by user's id and car's VIN code")
    public ResponseEntity<String> addCarToUserByVinCode(
            @PathVariable Long userId,
            @RequestBody VinCodeDto vinCode
    ) {
        try {
            carService.addCarToUserByVinCode(userId, vinCode);
            return ResponseEntity.ok().body("Vehicle added successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{userId}/delete/car/{vinCode}")
    @ApiOperation(value = "Delete a user's car by user's id and car's VIN code")
    public ResponseEntity<String> deleteCarFromUserByVinCode(
            @PathVariable Long userId,
            @PathVariable String vinCode
    ) {
        try {
            carService.deleteCarFromUserByVinCode(userId, vinCode);
            return ResponseEntity.ok().body("Vehicle successfully deleted");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}