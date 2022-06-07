package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    public ResponseEntity<?> getAllChargerUserChargers(@PathVariable Long chargerUserId) {
        try {
            return ResponseEntity.ok().body(chargerService.)
        }
    }
}
