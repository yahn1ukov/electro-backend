package ua.nure.andrii.yahniukov.complaint;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.complaint.DescriptionDto;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping("/users/{userId}/chargers/{chargerId}")
    @ApiOperation(value = "Create a complaint about charger by user id")
    public void createComplaintUserCharger(
            @PathVariable Long userId,
            @PathVariable Long chargerId,
            @RequestBody DescriptionDto description
    ) {
        complaintService.createComplaintUserCharger(userId, chargerId, description);
    }

    @PostMapping("/users/{userId}/stations/{stationId}")
    @ApiOperation(value = "Create a complaint about station by user id")
    public void createComplaintUserStation(
            @PathVariable Long userId,
            @PathVariable Long stationId,
            @RequestBody DescriptionDto description
    ) {
        complaintService.createComplaintUserStation(userId, stationId, description);
    }
}