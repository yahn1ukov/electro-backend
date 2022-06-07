package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.andrii.yahniukov.models.dto.CarDto;
import ua.nure.andrii.yahniukov.services.CarService;

@RestController
@RequestMapping("/api/v1/IoT")
@RequiredArgsConstructor
public class IoTController {
    private final CarService carService;

    @PostMapping("/create/car")
    @ApiOperation(value = "Get data and create a car")
    public void createCar(CarDto car) {
        carService.createCar(car);
    }
}
