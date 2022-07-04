package ua.nure.andrii.yahniukov.admin;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.dto.chargerUser.ChargerUserDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.dto.stationUser.StationUserDto;
import ua.nure.andrii.yahniukov.dto.user.FormRoleDto;
import ua.nure.andrii.yahniukov.dto.user.UserDto;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;
import ua.nure.andrii.yahniukov.user.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('admin:read', 'admin:write')")
public class AdminController {
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final StationUserService stationUserService;
    private final AdminService adminService;

    @GetMapping("/users")
    @ApiOperation(value = "View a list of users")
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/chargers")
    @ApiOperation(value = "View a list of charger users")
    public List<ChargerUserDto> getAllChargerUsers() {
        return chargerUserService.getAll();
    }

    @GetMapping("/users/stations")
    @ApiOperation(value = "View a list of station users")
    public List<StationUserDto> getAllStationUsers() {
        return stationUserService.getAll();
    }

    @GetMapping("/users/chargers/no-verification")
    @ApiOperation(value = "View a list of no verification station users")
    public List<ChargerUserDto> getAllNoVerificationChargerUsers() {
        return chargerUserService.getAllNoVerification();
    }

    @GetMapping("/users/stations/no-verification")
    @ApiOperation(value = "View a list of no verification station users")
    public List<StationUserDto> getAllNoVerificationStationUsers() {
        return stationUserService.getAllNoVerification();
    }

    @PatchMapping("/users/{userEmail}/role")
    @ApiOperation(value = "Change a user's role by id")
    public SuccessMessageDto changeRole(@PathVariable String userEmail, @RequestBody FormRoleDto role) {
        return userService.changeRole(userEmail, role);
    }

    @PatchMapping("/users/chargers/{chargerUserId}/verification")
    @ApiOperation(value = "Accept charger user by id")
    public void acceptChargerUser(@PathVariable Long chargerUserId) {
        chargerUserService.accept(chargerUserId);
    }

    @PatchMapping("/users/stations/{stationUserId}/verification")
    @ApiOperation(value = "Accept station user by id")
    public void acceptStationUser(@PathVariable Long stationUserId) {
        stationUserService.accept(stationUserId);
    }

    @PatchMapping("/users/{userId}/block")
    @ApiOperation(value = "Block a user by id")
    public void blockUser(@PathVariable Long userId) {
        userService.block(userId);
    }

    @PatchMapping("/users/chargers/{chargerUserId}/block")
    @ApiOperation(value = "Block a charger user by id")
    public void blockChargerUser(@PathVariable Long chargerUserId) {
        chargerUserService.block(chargerUserId);
    }

    @PatchMapping("/users/stations/{stationUserId}/block")
    @ApiOperation(value = "Block a station user by id")
    public void blockStationUser(@PathVariable Long stationUserId) {
        stationUserService.block(stationUserId);
    }

    @DeleteMapping("/users/{userId}/delete")
    @ApiOperation(value = "Delete a user by id")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @DeleteMapping("/users/chargers/{chargerUserId}/delete")
    @ApiOperation(value = "Delete a charger user by id")
    public void deleteChargerUser(@PathVariable Long chargerUserId) {
        chargerUserService.delete(chargerUserId);
    }

    @DeleteMapping("/users/stations/{stationUserId}/delete")
    @ApiOperation(value = "Delete a station user by id")
    public void deleteStationUser(@PathVariable Long stationUserId) {
        stationUserService.delete(stationUserId);
    }

    @PostMapping("/db/backup")
    @ApiOperation(value = "Create a backup of DB")
    public void backup() throws IOException, InterruptedException {
        adminService.backup();
    }

    @PostMapping("/db/restore")
    @ApiOperation(value = "Restore a backup of DB")
    public void restore() throws IOException, InterruptedException {
        adminService.restore();
    }
}