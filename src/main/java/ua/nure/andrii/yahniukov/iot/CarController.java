package ua.nure.andrii.yahniukov.iot;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.charger.ChargerService;
import ua.nure.andrii.yahniukov.dto.charger.ChargerDto;
import ua.nure.andrii.yahniukov.dto.iot.CarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormAddCarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormUpdateCarDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.dto.station.StationDto;
import ua.nure.andrii.yahniukov.station.StationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final ChargerService chargerService;
    private final StationService stationService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a car by IoT")
    public SuccessMessageDto create(@RequestBody FormAddCarDto car) {
        return carService.create(car);
    }

    @GetMapping("/{carId}")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a car by id")
    public CarDto getById(@PathVariable Long carId) {
        return carService.getById(carId);
    }

    @GetMapping("/vin-codes/{carVinCode}")
    @ApiOperation(value = "View a car by VIN code")
    public CarDto getByVinCode(@PathVariable String carVinCode) {
        return carService.getByVinCode(carVinCode);
    }

    @PatchMapping("/vin-codes/{carVinCode}/update")
    @ApiOperation(value = "Update a car by VIN code")
    public void updateByVinCode(@PathVariable String carVinCode, @RequestBody FormUpdateCarDto car) {
        carService.updateByVinCode(carVinCode, car);
    }

    @GetMapping("/vin-codes/{carVinCode}/chargers")
    @ApiOperation(value = "Get a list of chargers by car's geolocation and some data")
    public List<ChargerDto> getAllChargersForCar(@PathVariable String carVinCode) {
        return chargerService.getAllForCar(carVinCode);
    }

    @GetMapping("/vin-codes/{carVinCode}/stations")
    @ApiOperation(value = "Get a list of stations by car's geolocation and some data")
    public List<StationDto> getAllStationsForCar(@PathVariable String carVinCode) {
        return stationService.getAllForCar(carVinCode);
    }
}