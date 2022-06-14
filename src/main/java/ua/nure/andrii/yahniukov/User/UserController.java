package ua.nure.andrii.yahniukov.User;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.User.dto.UserDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('get:user')")
    @ApiOperation(value = "View a user by id")
    public UserDto getUser(@PathVariable String email) {
        return userService.getByEmail(email);
    }
}