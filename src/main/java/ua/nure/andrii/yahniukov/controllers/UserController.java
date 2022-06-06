package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.UserDto;
import ua.nure.andrii.yahniukov.security.dto.RegisterDto;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{id}")
    @ApiOperation(value = "View a user by id")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a user")
    public void createUser(@RequestBody RegisterDto user) {
        userService.createUser(user);
    }
}
