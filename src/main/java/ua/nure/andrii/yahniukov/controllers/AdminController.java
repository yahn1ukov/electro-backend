package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.RoleDto;
import ua.nure.andrii.yahniukov.models.dto.UserDto;
import ua.nure.andrii.yahniukov.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/get/user/all")
    @ApiOperation(value = "View a list of users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update/user/role/{id}")
    @ApiOperation(value = "Update role a user by id")
    public void updateUserRoleById(@PathVariable Long id, @RequestBody RoleDto role) {
        userService.updateUserRoleById(id, role);
    }

    @PatchMapping("/block/user/{id}")
    @ApiOperation(value = "Block a user by id")
    public void blockUserById(@PathVariable Long id) {
        userService.blockUserById(id);
    }

    @DeleteMapping("/delete/user/{id}")
    @ApiOperation(value = "Delete a user by id")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}