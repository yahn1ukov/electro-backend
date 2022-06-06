package ua.nure.andrii.yahniukov.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.models.dto.ChargerDto;
import ua.nure.andrii.yahniukov.services.ChargerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/charger")
@RequiredArgsConstructor
public class ChargerController {
    private final ChargerService chargerService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a charger")
    public void createCharger(@RequestBody ChargerDto charger) {
        chargerService.createCharger(charger);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "View a list of chargers")
    public List<ChargerDto> getAllCharger() {
        return chargerService.getAllCharger();
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "View a charger by id")
    public ChargerDto getChargerById(@PathVariable Long id) {
        return chargerService.getChargerById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a charger by id")
    public void deleteChargerById(@PathVariable Long id) {
        chargerService.deleteChargerById(id);
    }
}
