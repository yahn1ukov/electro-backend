package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.services.ChargerService;
import ua.nure.andrii.yahniukov.services.UserService;

@RestController
@RequestMapping("/api/v1/charger")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;
    private final UserService userService;

    @PostMapping("/create/partner")
    @ApiOperation(value = "Create a charger partner")
    public void createChargerUser(@RequestBody RegisterPartnerDto partner) {
        userService.createChargerUser(partner);
    }


}
