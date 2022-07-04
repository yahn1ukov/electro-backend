package ua.nure.andrii.yahniukov.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.iot.CarDto;
import ua.nure.andrii.yahniukov.dto.iot.FormVinCodeDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
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

    @GetMapping("/current")
    @PreAuthorize("hasAnyAuthority('admin:read', 'admin:write', 'user:read', 'user:write', 'moderator:read', 'moderator:write')")
    @ApiOperation(value = "View a user by id")
    public UserDto get(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId) {
        return userService.get(userId);
    }

    @PatchMapping("/current/car/add")
    @ApiOperation(value = "Add a car to user by user's id and car's VIN code")
    public SuccessMessageDto addCarToUserByVinCode(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId, @RequestBody FormVinCodeDto vinCode) {
        return carService.addByVinCode(userId, vinCode);
    }

    @DeleteMapping("/current/cars/{carId}/delete")
    @ApiOperation(value = "Delete a user's car by user's id and car's id")
    public void deleteCarFromUserByVinCode(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId, @PathVariable Long carId) {
        carService.delete(userId, carId);
    }

    @GetMapping("/current/cars")
    @ApiOperation(value = "View list of user's cars by user id")
    public List<CarDto> getAllCars(@ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId) {
        return carService.getAll(userId);
    }
}