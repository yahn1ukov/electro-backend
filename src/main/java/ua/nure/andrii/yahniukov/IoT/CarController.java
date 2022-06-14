package ua.nure.andrii.yahniukov.IoT;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.IoT.dto.CarDto;
import ua.nure.andrii.yahniukov.IoT.dto.FormCarDto;
import ua.nure.andrii.yahniukov.IoT.dto.FormVinCodeDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    @ApiOperation(value = "Get data and create a car")
    public void createCar(@RequestBody FormCarDto car) {
        carService.create(car);
    }

    @PutMapping("/add/users/{email}")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Add a car to user by user's id and car's VIN code")
    public void addCarToUserByVinCode(
            @PathVariable String email,
            @RequestBody FormVinCodeDto vinCode
    ) {
        carService.addByVinCode(email, vinCode);
    }

    @DeleteMapping("/{vinCode}/delete/by/users/{email}")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Delete a user's car by user's id and car's VIN code")
    public void deleteCarFromUserByVinCode(
            @PathVariable String email,
            @PathVariable String vinCode
    ) {
        carService.deleteByVinCode(email, vinCode);
    }

    @GetMapping("/{vinCode}/get")
    @PreAuthorize("hasAuthority('user:read')")
    @ApiOperation(value = "View a car by VIN code")
    public CarDto getCar(@PathVariable String vinCode) {
        return carService.getByVinCode(vinCode);
    }

    @GetMapping("/get/all/users/{email}")
    @PreAuthorize("hasAuthority('user:read')")
    @ApiOperation(value = "View list of user's cars by id")
    public List<CarDto> getAllCars(@PathVariable String email) {
        return carService.getAllForUser(email);
    }
}