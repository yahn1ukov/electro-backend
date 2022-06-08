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
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.security.models.dto.LoginDto;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterUserDto;
import ua.nure.andrii.yahniukov.security.services.AuthService;
import ua.nure.andrii.yahniukov.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
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
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDto registerUser) {
        try {
            userService.createUser(registerUser);
            return ResponseEntity.ok().body("User successfully created");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/register/charger/partner")
    @ApiOperation(value = "Register a charger partner")
    public ResponseEntity<String> registerChargerPartner(@RequestBody RegisterPartnerDto registerPartner) {
        try {
            userService.createChargerUser(registerPartner);
            return ResponseEntity.ok().body("Application successfully sent");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/register/station/partner")
    @ApiOperation(value = "Register a station partner")
    public ResponseEntity<String> registerStationPartner(@RequestBody RegisterPartnerDto registerPartner) {
        try {
            userService.createStationUser(registerPartner);
            return ResponseEntity.ok().body("Application successfully sent");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "Logout a partner")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}