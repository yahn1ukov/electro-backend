package ua.nure.andrii.yahniukov.models.dto.IoT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.IoT.CarEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CarDto {
    private Long id;
    private String name;
    private String model;
    private String vinCode;
    private Integer mileage;
    private String typeConnector;
    private Double percentageOfCharge;
    private Date createAt;

    public static CarDto fromCar(CarEntity car) {
        return CarDto.builder()
                .id(car.getId())
                .name(car.getName())
                .model(car.getModel())
                .vinCode(car.getVinCode())
                .mileage(car.getMileage())
                .typeConnector(car.getTypeConnector())
                .percentageOfCharge(car.getPercentageOfCharge())
                .createAt(car.getCreatedAt())
                .build();
    }
}
