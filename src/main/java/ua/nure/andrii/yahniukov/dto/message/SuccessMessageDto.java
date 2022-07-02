package ua.nure.andrii.yahniukov.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessMessageDto {
    private String message;
}
