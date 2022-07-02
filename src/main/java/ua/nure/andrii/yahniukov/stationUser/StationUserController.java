package ua.nure.andrii.yahniukov.stationUser;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.dto.stationUser.StationUserDto;

@RestController
@RequestMapping("/api/v1/users/stations")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('station:read', 'station:write')")
public class StationUserController {
    private final StationUserService stationUserService;

    @GetMapping("/{id}")
    @ApiOperation(value = "View a station user by id")
    public StationUserDto getUser(@PathVariable Long id) {
        return stationUserService.get(id);
    }
}