package ua.nure.andrii.yahniukov.chargerUser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.dto.chargerUser.ChargerUserDto;

@RestController
@RequestMapping("/api/v1/users/chargers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('charger:read', 'charger:write')")
public class ChargerUserController {
    private final ChargerUserService chargerUserService;

    @GetMapping("/current")
    @ApiOperation(value = "View a charger user by id")
    public ChargerUserDto getUser(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId) {
        return chargerUserService.get(userId);
    }
}