package ua.nure.andrii.yahniukov.ComplaintsUserCharger;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto.FormDescriptionDto;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintUserChargerController {
    private final ComplaintUserChargerService complaintUserChargerService;

    @PostMapping("/create/users/{email}/chargers/{code}")
    @ApiOperation(value = "Create a complaint about charger by user email")
    public void create(
            @PathVariable String email,
            @PathVariable String code,
            @RequestBody FormDescriptionDto complaint
    ) {
        complaintUserChargerService.create(email, code, complaint);
    }
}
