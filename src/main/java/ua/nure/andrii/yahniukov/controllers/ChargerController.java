package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormChargerDto;
import ua.nure.andrii.yahniukov.security.dto.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.services.ChargerService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/charger")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;
    private final UserService userService;

    @PostMapping("/create/partner")
    @ApiOperation(value = "Create a charger partner")
    public ResponseEntity<String> createChargerUser(@RequestBody RegisterPartnerDto partner) {
        try {
            userService.createChargerUser(partner);
            return ResponseEntity.ok().body("Application successfully sent");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/user/{chargerUserId}/create")
    @ApiOperation(value = "Create a charger by user's id")
    public ResponseEntity<String> createCharger(
            @PathVariable Long chargerUserId,
            @RequestBody FormChargerDto charger
    ) {
        try {
            chargerService.createCharger(chargerUserId, charger);
            return ResponseEntity.ok().body("");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{chargerUserId}")
    @ApiOperation(value = "View a charger user by id")
    public ResponseEntity<?> getChargerUserById(@PathVariable Long chargerUserId) {
        try {
            return ResponseEntity.ok().body(userService.getChargerUserById(chargerUserId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{chargerUserId}/get/all")
    @ApiOperation(value = "View a list of charger user's chargers")
    public ResponseEntity<?> getAllChargerUserChargers(@PathVariable Long chargerUserId) {
        try {
            return ResponseEntity.ok().body(chargerService.getAllChargerUserChargers(chargerUserId));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/user/{chargerUserId}/delete/{chargerId}")
    @ApiOperation(value = "Delete a charger by charger user id")
    public ResponseEntity<String> deleteChargerById(
            @PathVariable Long chargerUserId,
            @PathVariable Long chargerId
    ) {
        try {
            chargerService.deleteChargerById(chargerUserId, chargerId);
            return ResponseEntity.ok().body("");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
