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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
    @ApiOperation(value = "View a car by id")
    public CarDto getById(@PathVariable Long id) {
        return carService.getById(id);
    }

    @GetMapping("/vin-codes/{vinCode}")
    @ApiOperation(value = "View a car by VIN code")
    public CarDto getByVinCode(@PathVariable String vinCode) {
        return carService.getByVinCode(vinCode);
    }

    @PatchMapping("/vin-codes/{vinCode}/update")
    @ApiOperation(value = "Update a car by VIN code")
    public void updateByVinCode(@PathVariable String vinCode, @RequestBody FormUpdateCarDto car) {
        carService.updateByVinCode(vinCode, car);
    }

    @GetMapping("/vin-codes/{vinCode}/chargers")
    @ApiOperation(value = "Get a list of chargers by car's geolocation and some data")
    public List<ChargerDto> getAllChargersForCar(@PathVariable String vinCode) {
        return chargerService.getAllForCar(vinCode);
    }

    @GetMapping("/vin-codes/{vinCode}/stations")
    @ApiOperation(value = "Get a list of stations by car's geolocation and some data")
    public List<StationDto> getAllStationsForCar(@PathVariable String vinCode) {
        return stationService.getAllForCar(vinCode);
    }
}