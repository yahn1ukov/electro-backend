package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.entities.UserEntity;
import ua.nure.andrii.yahniukov.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @ApiOperation(value = "View a list of users")
    @GetMapping("/get/user/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Update role a user by id")
    @PutMapping("/update/user/role/{id}")
    public void updateUserRoleById(@PathVariable Long id, String role) {
        userService.updateUserRoleById(id, role);
    }

    @ApiOperation(value = "Block a user by id")
    @PatchMapping("/block/user/{id}")
    public void blockUserById(@PathVariable Long id) {
        userService.blockUserById(id);
    }

    @ApiOperation(value = "Delete a user by id")
    @DeleteMapping("/delete/user/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}