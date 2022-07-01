package ua.nure.andrii.yahniukov.dto.iot;

import lombok.Builder;
import ua.nure.andrii.yahniukov.iot.CarEntity;

import java.util.Date;

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