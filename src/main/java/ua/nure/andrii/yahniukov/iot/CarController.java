package ua.nure.andrii.yahniukov.iot;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.iot.AddCarDto;
import ua.nure.andrii.yahniukov.dto.iot.CarDto;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    @ApiOperation(value = "Create a car by IoT")
    public void createCar(@RequestBody AddCarDto car) {
        carService.create(car);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View a car by id")
    public CarDto get(@PathVariable Long id) {
        return carService.get(id);
    }
}