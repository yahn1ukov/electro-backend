package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormChargerDto;
import ua.nure.andrii.yahniukov.services.ChargerService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/charger")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;
    private final UserService userService;

    @PostMapping("/user/{chargerUserId}/create")
    @PreAuthorize("hasAuthority('charger:write')")
    @ApiOperation(value = "Create a charger by user's id")
    public ResponseEntity<String> createCharger(
            @PathVariable Long chargerUserId,
            @RequestBody FormChargerDto charger
    ) {
        try {
            chargerService.createCharger(chargerUserId, charger);
            return ResponseEntity.ok().body("Charging station added successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{chargerUserId}")
    @PreAuthorize("hasAuthority('charger:read')")
    @ApiOperation(value = "View a charger user by id")
    public ResponseEntity<?> getChargerUserById(@PathVariable Long chargerUserId) {
        try {
            return ResponseEntity.ok().body(userService.getChargerUserById(chargerUserId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{chargerUserId}/get/all")
    @PreAuthorize("hasAuthority('charger:read')")
    @ApiOperation(value = "View a list of charger user's chargers")
    public ResponseEntity<?> getAllChargerUserChargers(@PathVariable Long chargerUserId) {
        try {
            return ResponseEntity.ok().body(chargerService.getAllChargerUserChargers(chargerUserId));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/get/charger/all")
    @ApiOperation(value = "View a list of chargers")
    public ResponseEntity<?> getAllChargers() {
        try {
            return ResponseEntity.ok().body(chargerService.getAllChargers());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/user/{chargerUserId}/delete/{chargerId}")
    @PreAuthorize("hasAuthority('charger:write')")
    @ApiOperation(value = "Delete a charger by charger user id")
    public ResponseEntity<String> deleteChargerById(
            @PathVariable Long chargerUserId,
            @PathVariable Long chargerId
    ) {
        try {
            chargerService.deleteChargerById(chargerUserId, chargerId);
            return ResponseEntity.ok().body("Charging station deleted successfully");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/change/isCharging/{chargerName}")
    @ApiOperation(value = "Change state charging a charger")
    public void changeIsCharging(@PathVariable String chargerName) {
        chargerService.changeIsCharging(chargerName);
    }

    @PutMapping("/change/isBroken/{chargerName}")
    @ApiOperation(value = "Change state broken a charger")
    public void changeIsBroken(@PathVariable String chargerName) {
        chargerService.changeIsBroken(chargerName);
    }
}
