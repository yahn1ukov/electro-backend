package ua.nure.andrii.yahniukov.security.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.security.dto.login.LoginRequestDto;
import ua.nure.andrii.yahniukov.security.dto.login.LoginResponseDto;
import ua.nure.andrii.yahniukov.security.dto.registration.RegistrationRequestPartnerDto;
import ua.nure.andrii.yahniukov.security.dto.registration.RegistrationRequestUserDto;
import ua.nure.andrii.yahniukov.security.service.AuthService;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;
import ua.nure.andrii.yahniukov.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final StationUserService stationUserService;
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login user")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginUser) {
        return authService.login(loginUser);
    }

    @PostMapping("/registration/users")
    @ApiOperation(value = "Register a user")
    public SuccessMessageDto registerUser(@RequestBody RegistrationRequestUserDto registerUser) {
        return userService.create(registerUser);
    }

    @PostMapping("/registration/users/chargers")
    @ApiOperation(value = "Register a charger partner")
    public SuccessMessageDto registerChargerPartner(@RequestBody RegistrationRequestPartnerDto registerPartner) {
        return chargerUserService.create(registerPartner);
    }

    @PostMapping("/registration/users/stations")
    @ApiOperation(value = "Register a station partner")
    public SuccessMessageDto registerStationPartner(@RequestBody RegistrationRequestPartnerDto registerPartner) {
        return stationUserService.create(registerPartner);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:write', 'user:read', 'user:write', 'moderator:read', 'moderator:write', 'station:read', 'station:write', 'charger:read', 'charger:write')")
    @ApiOperation(value = "Logout user")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}