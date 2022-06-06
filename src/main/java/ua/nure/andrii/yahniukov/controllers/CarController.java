package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.entities.CarEntity;
import ua.nure.andrii.yahniukov.services.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/get/{vinCode}")
    @ApiOperation(value = "View a car by VIN code")
    public CarEntity getCarByVinCode(@PathVariable String vinCode) {
        return carService.getCarByVinCode(vinCode);
    }

    @GetMapping("/get/all/user/{userId}")
    @ApiOperation(value = "View list of user's cars")
    public List<CarEntity> getAllUserCars(@PathVariable Long userId) {
        return carService.getAllUserCars(userId);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a car")
    public void createCar(@RequestBody CarEntity car) {
        carService.createCar(car);
    }

    @PutMapping("/add/user/{userId}")
    @ApiOperation(value = "Add a car to a user by id and VIN code")
    public void addCarToUserByVinCode(@PathVariable Long userId, @RequestBody String vinCode) {
        carService.addCarToUserByVinCode(userId, vinCode);
    }

    @DeleteMapping("/delete/user/{userId}")
    @ApiOperation(value = "Delete a car from a user by id and VIN code")
    public void deleteCarFromUserByVinCode(@PathVariable Long userId, String vinCode) {
        carService.deleteCarFromUserByVinCode(userId, vinCode);
    }
}
