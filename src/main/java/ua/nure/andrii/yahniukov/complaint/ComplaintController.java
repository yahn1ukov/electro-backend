package ua.nure.andrii.yahniukov.complaint;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.andrii.yahniukov.dto.complaint.FormDescriptionDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('user:read', 'user:write')")
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping("/users/current/chargers/{chargerId}")
    @ApiOperation(value = "Create a complaint about charger by user id")
    public SuccessMessageDto createComplaintUserCharger(
            @ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId,
            @PathVariable Long chargerId,
            @RequestBody FormDescriptionDto description
    ) {
        return complaintService.createComplaintUserCharger(userId, chargerId, description);
    }

    @PostMapping("/users/current/stations/{stationId}")
    @ApiOperation(value = "Create a complaint about station by user id")
    public SuccessMessageDto createComplaintUserStation(
            @ApiParam(hidden = true) @RequestAttribute(value = "userId") Long userId,
            @PathVariable Long stationId,
            @RequestBody FormDescriptionDto description
    ) {
        return complaintService.createComplaintUserStation(userId, stationId, description);
    }
}