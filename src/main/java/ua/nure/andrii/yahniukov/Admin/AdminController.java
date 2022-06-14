package ua.nure.andrii.yahniukov.Admin;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.ChargerUser.dto.ChargerUserNoVerificationDto;
import ua.nure.andrii.yahniukov.ChargerUser.dto.ChargerUserVerificationDto;
import ua.nure.andrii.yahniukov.StationUser.StationUserService;
import ua.nure.andrii.yahniukov.StationUser.dto.StationUserNoVerificationDto;
import ua.nure.andrii.yahniukov.StationUser.dto.StationUserVerificationDto;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.User.dto.FormRoleDto;
import ua.nure.andrii.yahniukov.User.dto.UserDto;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final StationUserService stationUserService;
    private final AdminService adminService;

    @GetMapping("/get/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of users")
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/get/charger/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of charger users")
    public List<ChargerUserVerificationDto> getAllChargerUsers() {
        return chargerUserService.getAll();
    }

    @GetMapping("/get/station/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of station users")
    public List<StationUserVerificationDto> getAllStationUsers() {
        return stationUserService.getAll();
    }

    @GetMapping("/get/no-verification/charger/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of no verification station users")
    public List<ChargerUserNoVerificationDto> getAllNoVerificationChargerUsers() {
        return chargerUserService.getAllNoVerification();
    }

    @GetMapping("/get/no-verification/station/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @ApiOperation(value = "View a list of no verification station users")
    public List<StationUserNoVerificationDto> getAllNoVerificationStationUsers() {
        return stationUserService.getAllNoVerification();
    }

    @PatchMapping("/change/role/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Change a user's role by email")
    public void changeRoleUser(
            @PathVariable String email,
            @RequestBody FormRoleDto role
    ) {
        adminService.changeRole(email, role);
    }

    @PutMapping("/accept/verification/charger/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Verification charger user by id")
    public void acceptVerificationChargerUser(@PathVariable String email) {
        adminService.acceptVerificationChargerUser(email);
    }

    @PutMapping("/accept/verification/station/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Verification charger user by email")
    public void acceptVerificationStationUser(@PathVariable String email) {
        adminService.acceptVerificationStationUser(email);
    }

    @PutMapping("/block/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Block a user by email")
    public void blockUser(@PathVariable String email) {
        userService.blockByEmail(email);
    }

    @PutMapping("/block/charger/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Block a charger user by email")
    public void blockChargerUser(@PathVariable String email) {
        chargerUserService.blockByEmail(email);
    }

    @PutMapping("/block/station/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Block a station user by email")
    public void blockStationUser(@PathVariable String email) {
        stationUserService.blockByEmail(email);
    }

    @DeleteMapping("/delete/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Delete a user by email")
    public void deleteUser(@PathVariable String email) {
        userService.deleteByEmail(email);
    }

    @DeleteMapping("/delete/charger/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Delete a charger user by email")
    public void deleteChargerUser(@PathVariable String email) {
        chargerUserService.deleteByEmail(email);
    }

    @DeleteMapping("/delete/station/users/{email}")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Delete a station user by id")
    public void deleteStationUser(@PathVariable String email) {
        stationUserService.deleteByEmail(email);
    }

    @PostMapping("/create/backup/db")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Create a backup of DB")
    public boolean backupDB() throws IOException, InterruptedException {
        return adminService.backupDB();
    }

    @PostMapping("/restore/backup/db")
    @PreAuthorize("hasAuthority('admin:write')")
    @ApiOperation(value = "Restore a backup of DB")
    public boolean restoreDB() throws IOException, InterruptedException {
        return adminService.restoreDB();
    }
}