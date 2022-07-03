package ua.nure.andrii.yahniukov.dto.iot;

import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.iot.CarEntity;

import java.util.Date;

@Data
@Builder
public class CarDto {
    private Long id;
    private String name;
    private String model;
    private String vinCode;
    private Double latitude;
    private Double longitude;
    private Integer mileage;
    private String typeConnector;
    private Double percentageOfCharge;
    private Date createdAt;

    public static CarDto fromCar(CarEntity car) {
        return CarDto.builder()
                .id(car.getId())
                .name(car.getName())
                .model(car.getModel())
                .vinCode(car.getVinCode())
                .latitude(car.getLatitude())
                .longitude(car.getLongitude())
                .mileage(car.getMileage())
                .typeConnector(car.getTypeConnector())
                .percentageOfCharge(car.getPercentageOfCharge())
                .createdAt(car.getCreatedAt())
                .build();
    }
}