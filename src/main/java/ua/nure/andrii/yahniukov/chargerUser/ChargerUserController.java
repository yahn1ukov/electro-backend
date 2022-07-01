package ua.nure.andrii.yahniukov.chargerUser;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.dto.chargerUser.ChargerUserDto;

@RestController
@RequestMapping("/api/v1/users/chargers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_CHARGER')")
public class ChargerUserController {
    private final ChargerUserService chargerUserService;

    @GetMapping("/{id}")
    @ApiOperation(value = "View a charger user by id")
    public ChargerUserDto getUser(@PathVariable Long id) {
        return chargerUserService.get(id);
    }
}