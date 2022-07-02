package ua.nure.andrii.yahniukov.user;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.iot.CarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormVinCodeDto;
import ua.nure.andrii.yahniukov.dto.user.UserDto;
import ua.nure.andrii.yahniukov.iot.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
public class UserController {
    private final UserService userService;
    private final CarService carService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:write', 'user:read', 'user:write', 'moderator:read', 'moderator:write')")
    @ApiOperation(value = "View a user by id")
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}/car/add")
    @ApiOperation(value = "Add a car to user by user's id and car's VIN code")
    public void addCarToUserByVinCode(@PathVariable Long id, @RequestBody FormVinCodeDto vinCode) {
        carService.addByVinCode(id, vinCode);
    }

    @DeleteMapping("/{id}/cars/{carId}/delete")
    @ApiOperation(value = "Delete a user's car by user's id and car's id")
    public void deleteCarFromUserByVinCode(@PathVariable Long id, @PathVariable Long carId) {
        carService.delete(id, carId);
    }

    @GetMapping("/{id}/cars")
    @ApiOperation(value = "View list of user's cars by user id")
    public List<CarDto> getAllCars(@PathVariable Long id) {
        return carService.getAll(id);
    }
}