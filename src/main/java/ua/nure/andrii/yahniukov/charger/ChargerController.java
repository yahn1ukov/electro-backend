package ua.nure.andrii.yahniukov.charger;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.charger.ChargerDto;
import ua.nure.andrii.yahniukov.dto.charger.FormChargerDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chargers")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;

    @PostMapping("/create/by/charger/users/{email}")
    @PreAuthorize("hasAuthority('charger:write')")
    @ApiOperation(value = "Create a charger by user's email")
    public void createCharger(
            @PathVariable String email,
            @RequestBody FormChargerDto charger
    ) {
        chargerService.create(email, charger);
    }

    @GetMapping("/get/all/for/charger/users/{email}")
    @PreAuthorize("hasAuthority('charger:read')")
    @ApiOperation(value = "View a list of charger user's chargers")
    public List<ChargerDto> getAllForChargerUser(@PathVariable String email) {
        return chargerService.getAllForChargerUser(email);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "View a list of chargers")
    public List<ChargerDto> getAll() {
        return chargerService.getAll();
    }

    @GetMapping("/{code}/get")
    @ApiOperation(value = "View a charger")
    public ChargerDto getByCode(@PathVariable String code) {
        return chargerService.getByCode(code);
    }

    @DeleteMapping("/{code}/delete/by/charger/users/{email}")
    @PreAuthorize("hasAuthority('charger:write')")
    @ApiOperation(value = "Delete a charger by charger user id")
    public void deleteByCode(
            @PathVariable String email,
            @PathVariable String code
    ) {
        chargerService.deleteByCode(email, code);
    }

    @PatchMapping("/{code}/change/is-charging")
    @ApiOperation(value = "Change state charging a charger")
    public void changeIsCharging(@PathVariable String code) {
        chargerService.changeIsCharging(code);
    }

    @PatchMapping("/{code}/change/is-broken")
    @ApiOperation(value = "Change state broken a charger")
    public void changeIsBroken(@PathVariable String code) {
        chargerService.changeIsBroken(code);
    }

    @GetMapping("/{latitude}/{longitude}/{percentOfBattery}/{typeConnector}/{radius}")
    @ApiOperation(value = "Get a list of chargers by geolocation of car and some data")
    public List<ChargerDto> getAllChargersForCar(
            @PathVariable Long latitude,
            @PathVariable Long longitude,
            @PathVariable Long percentOfBattery,
            @PathVariable String typeConnector,
            @PathVariable Integer radius
    ) {
        return chargerService.getAllForCar(latitude, longitude, percentOfBattery, typeConnector, radius);
    }
}