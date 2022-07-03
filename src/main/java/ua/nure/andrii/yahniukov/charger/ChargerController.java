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
@PreAuthorize("hasAnyAuthority('charger:read', 'charger:write')")
public class ChargerController {
    private final ChargerService chargerService;

    @PostMapping("/users/{userId}/create")
    @ApiOperation(value = "Create a charger by charger user's id")
    public void create(@PathVariable Long userId, @RequestBody FormChargerDto charger) {
        chargerService.create(userId, charger);
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "View a list of charger user's chargers by id")
    public List<ChargerDto> getAllForChargerUser(@PathVariable Long userId) {
        return chargerService.getAllForChargerUser(userId);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a list of chargers")
    public List<ChargerDto> getAll() {
        return chargerService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('charger:read', 'charger:write', 'user:read', 'user:write')")
    @ApiOperation(value = "View a charger by id")
    public ChargerDto get(@PathVariable Long id) {
        return chargerService.get(id);
    }

    @DeleteMapping("/{chargerId}/users/{userId}/delete")
    @ApiOperation(value = "Delete a charger by charger user's id")
    public void delete(@PathVariable Long userId, @PathVariable Long chargerId) {
        chargerService.delete(userId, chargerId);
    }

    @PatchMapping("/{id}/charge")
    @ApiOperation(value = "Change state charging a charger by id")
    public void changeIsCharging(@PathVariable Long id) {
        chargerService.changeIsCharging(id);
    }

    @PatchMapping("/{id}/broke")
    @ApiOperation(value = "Change state broken a charger by id")
    public void changeIsBroken(@PathVariable Long id) {
        chargerService.changeIsBroken(id);
    }
}