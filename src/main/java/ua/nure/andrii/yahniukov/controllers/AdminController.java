package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.forms.FormRoleDto;
import ua.nure.andrii.yahniukov.models.dto.helpers.PartnerNoVerificationDto;
import ua.nure.andrii.yahniukov.models.dto.helpers.PartnerVerificationDto;
import ua.nure.andrii.yahniukov.models.dto.users.UserDto;
import ua.nure.andrii.yahniukov.services.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/get/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/charger/verification/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of charger users")
    public List<PartnerVerificationDto> getAllChargerUsers() {
        return userService.getAllChargerUsers();
    }

    @GetMapping("/get/station/verification/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of station users")
    public List<PartnerVerificationDto> getAllStationUsers() {
        return userService.getAllStationUsers();
    }

    @GetMapping("/get/charger/no/verification/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of no verification station users")
    public List<PartnerNoVerificationDto> getAllNoVerificationChargerUsers() {
        return userService.getAllNoVerificationChargerUsers();
    }

    @GetMapping("/get/station/no/verification/user/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of no verification station users")
    public List<PartnerNoVerificationDto> getAllNoVerificationStationUsers() {
        return userService.getAllNoVerificationStationUsers();
    }

    @PutMapping("/change/role/user/{userId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Change a user's role by id")
    public void changeRoleUser(
            @PathVariable Long userId,
            @RequestBody FormRoleDto role
    ) {
        userService.changeRoleUser(userId, role);
    }

    @PutMapping("/accept/verification/charger/user/{chargerUserId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Verification charger user by id")
    public void acceptVerificationChargerUser(@PathVariable Long chargerUserId) {
        userService.acceptVerificationChargerUser(chargerUserId);
    }

    @PutMapping("/accept/verification/station/user/{stationUserId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Verification charger user by id")
    public void acceptVerificationStationUser(@PathVariable Long stationUserId) {
        userService.acceptVerificationStationUser(stationUserId);
    }

    @PutMapping("/block/user/{userId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Block a user by id")
    public void blockUser(@PathVariable Long userId) {
        userService.blockUser(userId);
    }

    @PutMapping("/block/charger/user/{chargerUserId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Block a charger user by id")
    public void blockChargerUser(@PathVariable Long chargerUserId) {
        userService.blockChargerUser(chargerUserId);
    }

    @PutMapping("/block/station/user/{stationUserId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Block a station user by id")
    public void blockStationUser(@PathVariable Long stationUserId) {
        userService.blockStationUser(stationUserId);
    }

    @DeleteMapping("/delete/user/{userId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Delete a user by id")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping("/delete/charger/user/{chargerUserId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Delete a charger user by id")
    public void deleteChargerUser(@PathVariable Long chargerUserId) {
        userService.deleteChargerUser(chargerUserId);
    }

    @DeleteMapping("/delete/station/user/{stationUserId}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Delete a station user by id")
    public void deleteStationUser(@PathVariable Long stationUserId) {
        userService.deleteStationUser(stationUserId);
    }

    @PostMapping("/create/backup/db")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Create a backup of DB")
    public boolean backupDB() throws IOException, InterruptedException {
        return userService.backupDB();
    }

    @PostMapping("/restore/backup/db")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Restore a backup of DB")
    public boolean restoreDB() throws IOException, InterruptedException {
        return userService.restoreDB();
    }
}