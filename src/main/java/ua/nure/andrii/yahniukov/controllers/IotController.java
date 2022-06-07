package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormCarDto;
import ua.nure.andrii.yahniukov.services.CarService;
import ua.nure.andrii.yahniukov.services.ChargerService;

@RestController
@RequestMapping("/api/v1/iot")
@RequiredArgsConstructor
public class IotController {
    private final CarService carService;
    private final ChargerService chargerService;

    @PostMapping("/create/car")
    @ApiOperation(value = "Get data and create a car")
    public ResponseEntity<String> createCar(@RequestBody FormCarDto car) {
        try {
            carService.createCar(car);
            return ResponseEntity.ok().body("Vehicle successfully created");
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
