package ua.nure.andrii.yahniukov.ChargerUser;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.ChargerUser.dto.ChargerUserDto;

@RestController
@RequestMapping("/api/v1/charger/users")
@RequiredArgsConstructor
public class ChargerUserController {
    private final ChargerUserService chargerUserService;

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('get:partner')")
    @ApiOperation(value = "View a charger user by email")
    public ChargerUserDto getUser(@PathVariable String email) {
        return chargerUserService.getByEmail(email);
    }
}
