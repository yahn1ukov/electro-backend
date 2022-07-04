package ua.nure.andrii.yahniukov.charger;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.charger.ChargerDto;
import ua.nure.andrii.yahniukov.dto.charger.FormChargerDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chargers")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;

    @PostMapping("/users/current/create")
    @PreAuthorize("hasAnyAuthority('charger:read', 'charger:write')")
    @ApiOperation(value = "Create a charger by charger user's id")
    public SuccessMessageDto create(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId, @RequestBody FormChargerDto charger) {
        return chargerService.create(userId, charger);
    }

    @GetMapping("/users/current")
    @PreAuthorize("hasAnyAuthority('charger:read', 'charger:write')")
    @ApiOperation(value = "View a list of charger user's chargers by id")
    public List<ChargerDto> getAllForChargerUser(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId) {
        return chargerService.getAllForChargerUser(userId);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a list of chargers")
    public List<ChargerDto> getAll() {
        return chargerService.getAll();
    }

    @GetMapping("/{chargerId}")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a charger by id")
    public ChargerDto get(@PathVariable Long chargerId) {
        return chargerService.get(chargerId);
    }

    @DeleteMapping("/{chargerId}/users/current/delete")
    @PreAuthorize("hasAnyAuthority('charger:read', 'charger:write')")
    @ApiOperation(value = "Delete a charger by charger user's id")
    public void delete(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId, @PathVariable Long chargerId) {
        chargerService.delete(userId, chargerId);
    }

    @PatchMapping("/codes/{chargerCode}/charge")
    @ApiOperation(value = "Change state charging a charger by id")
    public void changeIsCharging(@PathVariable String chargerCode) {
        chargerService.changeIsCharging(chargerCode);
    }

    @PatchMapping("/codes/{chargerCode}/broke")
    @ApiOperation(value = "Change state broken a charger by id")
    public void changeIsBroken(@PathVariable String chargerCode) {
        chargerService.changeIsBroken(chargerCode);
    }
}