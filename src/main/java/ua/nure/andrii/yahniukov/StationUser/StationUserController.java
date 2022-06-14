package ua.nure.andrii.yahniukov.StationUser;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.StationUser.dto.StationUserDto;

@RestController
@RequestMapping("/api/v1/station/users")
@RequiredArgsConstructor
public class StationUserController {
    private final StationUserService stationUserService;

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('get:user')")
    @ApiOperation(value = "View a user by id")
    public StationUserDto getUser(@PathVariable String email) {
        return stationUserService.getByEmail(email);
    }
}
