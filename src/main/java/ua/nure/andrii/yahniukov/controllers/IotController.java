package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.forms.FormCarDto;
import ua.nure.andrii.yahniukov.models.dto.maintenances.ChargerDto;
import ua.nure.andrii.yahniukov.services.CarService;
import ua.nure.andrii.yahniukov.services.ChargerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/iot")
@RequiredArgsConstructor
public class IotController {
    private final CarService carService;
    private final ChargerService chargerService;

    @PostMapping("/create/car")
    @ApiOperation(value = "Get data and create a car")
    public void createCar(@RequestBody FormCarDto car) {
        carService.createCar(car);
    }

    @GetMapping("/latitude/{latitude}/longitude/{longitude}/percentOfBattery{percentOfBattery}/typeConnector/{typeConnector}")
    @ApiOperation(value = "Get a list of chargers by geolocation of car and some data")
    public List<ChargerDto> getAllChargersForCar(
            @PathVariable Long latitude,
            @PathVariable Long longitude,
            @PathVariable Long percentOfBattery,
            @PathVariable String typeConnector
    ) {
        return chargerService.getAllChargersForCar(latitude, longitude, percentOfBattery, typeConnector);
    }
}
