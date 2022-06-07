package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.security.dto.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.services.StationService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;
    private final UserService userService;

    @PostMapping("/create/partner")
    @ApiOperation(value = "Create a station partner")
    public ResponseEntity<String> createStationUser(@RequestBody RegisterPartnerDto partner) {
        try {
            userService.createStationUser(partner);
            return ResponseEntity.ok().body("Application successfully sent");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{stationUserId}")
    @ApiOperation(value = "View a station user by id")
    public ResponseEntity<?> getStationUserById(@PathVariable Long stationUserId) {
        try {
            return ResponseEntity.ok().body(userService.getStationUserById(stationUserId));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
