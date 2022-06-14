package ua.nure.andrii.yahniukov.ComplaintsUserStation;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto.FormDescriptionDto;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintUserStationController {
    private final ComplaintUserStationService complaintUserStationService;

    @PostMapping("/create/users/{email}/stations/{name}")
    @PreAuthorize("hasAuthority('user:write')")
    @ApiOperation(value = "Create a complaint about station by user email")
    public void create(
            @PathVariable String email,
            @PathVariable String name,
            @RequestBody FormDescriptionDto complaint
    ) {
        complaintUserStationService.create(email, name, complaint);
    }
}
