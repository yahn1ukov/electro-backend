package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.ComplaintDto;
import ua.nure.andrii.yahniukov.models.dto.UserDto;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterUserDto;
import ua.nure.andrii.yahniukov.services.ComplaintService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ComplaintService complaintService;

    @GetMapping("/get/{id}")
    @ApiOperation(value = "View a user by id")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a user")
    public void createUser(@RequestBody RegisterUserDto user) {
        userService.createUser(user);
    }

    @PostMapping("/{userId}/create/complaint/charger/{chargerId}")
    @ApiOperation(value = "Create a complaint of charger by user")
    public void createComplaintUserCharger(
            @PathVariable Long userId,
            @PathVariable Long chargerId,
            @RequestBody ComplaintDto complaint
    ) {
        complaintService.createComplaintUserCharger(userId, chargerId, complaint);
    }

    @PostMapping("/{userId}/create/complaint/station/{stationId}")
    @ApiOperation(value = "Create a complaint of station by user")
    public void createComplaintUserStation(
            @PathVariable Long userId,
            @PathVariable Long stationId,
            @RequestBody ComplaintDto complaint
    ) {
        complaintService.createComplaintUserStation(userId, stationId, complaint);
    }
}
