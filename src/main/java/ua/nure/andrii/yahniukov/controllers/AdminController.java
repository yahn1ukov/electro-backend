package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormRoleDto;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/get/user/all")
    @ApiOperation(value = "View a list of users")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllUsers());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unable to get list of users");
        }
    }

    @GetMapping("/get/charger/user/all")
    @ApiOperation(value = "View a list of charger users")
    public ResponseEntity<?> getAllChargerUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllChargerUsers());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/station/user/all")
    @ApiOperation(value = "View a list of station users")
    public ResponseEntity<?> getAllStationUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllStationUsers());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/charger/no/verification/user/all")
    @ApiOperation(value = "View a list of no verification station users")
    public ResponseEntity<?> getAllNoVerificationChargerUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllNoVerificationChargerUsers());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/station/no/verification/user/all")
    @ApiOperation(value = "View a list of no verification station users")
    public ResponseEntity<?> getAllNoVerificationStationUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllNoVerificationStationUsers());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/change/role/user/{userId}")
    @ApiOperation(value = "Change a user's role by id")
    public ResponseEntity<String> changeRoleUser(
            @PathVariable Long userId,
            @RequestBody FormRoleDto role
    ) {
        try {
            userService.changeRoleUser(userId, role);
            return ResponseEntity.ok().body("Role changed successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/accept/verification/charger/user/{chargerUserId}")
    @ApiOperation(value = "Verification charger user by id")
    public ResponseEntity<String> acceptVerificationChargerUser(@PathVariable Long chargerUserId) {
        try {
            userService.acceptVerificationChargerUser(chargerUserId);
            return ResponseEntity.ok().body("");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/accept/verification/station/user/{stationUserId}")
    @ApiOperation(value = "Verification charger user by id")
    public ResponseEntity<String> acceptVerificationStationUser(@PathVariable Long stationUserId) {
        try {
            userService.acceptVerificationStationUser(stationUserId);
            return ResponseEntity.ok().body("");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/block/user/{userId}")
    @ApiOperation(value = "Block a user by id")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {
        try {
            userService.blockUser(userId);
            return ResponseEntity.ok().body("User has been successfully blocked");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/block/charger/user/{chargerUserId}")
    @ApiOperation(value = "Block a charger user by id")
    public ResponseEntity<String> blockChargerUser(@PathVariable Long chargerUserId) {
        try {
            userService.blockChargerUser(chargerUserId);
            return ResponseEntity.ok().body("User has been successfully blocked");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/block/station/user/{stationUserId}")
    @ApiOperation(value = "Block a station user by id")
    public ResponseEntity<String> blockStationUser(@PathVariable Long stationUserId) {
        try {
            userService.blockStationUser(stationUserId);
            return ResponseEntity.ok().body("User has been successfully blocked");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/user/{userId}")
    @ApiOperation(value = "Delete a user by id")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body("User successfully deleted");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/charger/user/{chargerUserId}")
    @ApiOperation(value = "Delete a charger user by id")
    public ResponseEntity<String> deleteChargerUser(@PathVariable Long chargerUserId) {
        try {
            userService.deleteChargerUser(chargerUserId);
            return ResponseEntity.ok().body("User successfully deleted");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/station/user/{stationUserId}")
    @ApiOperation(value = "Delete a station user by id")
    public ResponseEntity<String> deleteStationUser(@PathVariable Long stationUserId) {
        try {
            userService.deleteStationUser(stationUserId);
            return ResponseEntity.ok().body("User successfully deleted");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}