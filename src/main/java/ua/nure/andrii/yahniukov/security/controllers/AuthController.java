package ua.nure.andrii.yahniukov.security.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.StationUser.StationUserService;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.security.models.dto.LoginDto;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterUserDto;
import ua.nure.andrii.yahniukov.security.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final StationUserService stationUserService;
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login user")
    public ResponseEntity<?> login(@RequestBody LoginDto loginUser) {
        try {
            return ResponseEntity.ok().body(authService.login(loginUser));
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register/user")
    @ApiOperation(value = "Register a user")
    public void registerUser(@RequestBody RegisterUserDto registerUser) {
        userService.create(registerUser);
    }

    @PostMapping("/register/charger/partner")
    @ApiOperation(value = "Register a charger partner")
    public void registerChargerPartner(@RequestBody RegisterPartnerDto registerPartner) {
        chargerUserService.create(registerPartner);
    }

    @PostMapping("/register/station/partner")
    @ApiOperation(value = "Register a station partner")
    public void registerStationPartner(@RequestBody RegisterPartnerDto registerPartner) {
        stationUserService.create(registerPartner);
    }
}