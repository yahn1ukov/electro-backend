package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.forms.FormChargerDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.ChargerDto;
import ua.nure.andrii.yahniukov.models.dto.users.PartnerDto;
import ua.nure.andrii.yahniukov.services.ChargerService;
import ua.nure.andrii.yahniukov.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/charger")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;
    private final UserService userService;

    @PostMapping("/user/{chargerUserId}/create")
    @PreAuthorize("hasAuthority('charger:write')")
    @ApiOperation(value = "Create a charger by user's id")
    public void createCharger(
            @PathVariable Long chargerUserId,
            @RequestBody FormChargerDto charger
    ) {
        chargerService.createCharger(chargerUserId, charger);
    }

    @GetMapping("/user/{chargerUserId}")
    @PreAuthorize("hasAuthority('get:partner')")
    @ApiOperation(value = "View a charger user by id")
    public PartnerDto getChargerUserById(@PathVariable Long chargerUserId) {
        return userService.getChargerUserById(chargerUserId);
    }

    @GetMapping("/user/{chargerUserId}/get/all")
    @PreAuthorize("hasAuthority('charger:read')")
    @ApiOperation(value = "View a list of charger user's chargers")
    public List<ChargerDto> getAllChargerUserChargers(@PathVariable Long chargerUserId) {
        return chargerService.getAllChargerUserChargers(chargerUserId);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "View a list of chargers")
    public List<ChargerDto> getAllChargers() {
        return chargerService.getAllChargers();
    }

    @DeleteMapping("/user/{chargerUserId}/delete/{chargerId}")
    @PreAuthorize("hasAuthority('charger:write')")
    @ApiOperation(value = "Delete a charger by charger user id")
    public void deleteChargerById(
            @PathVariable Long chargerUserId,
            @PathVariable Long chargerId
    ) {
        chargerService.deleteChargerById(chargerUserId, chargerId);
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