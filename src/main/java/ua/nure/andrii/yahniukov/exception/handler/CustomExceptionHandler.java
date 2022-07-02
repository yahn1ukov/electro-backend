package ua.nure.andrii.yahniukov.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.nure.andrii.yahniukov.exception.BadRequestException;
import ua.nure.andrii.yahniukov.exception.charger.ChargerAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.charger.ChargerNotFoundException;
import ua.nure.andrii.yahniukov.exception.complaint.ComplaintNotFoundException;
import ua.nure.andrii.yahniukov.exception.iot.CarAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.iot.CarNotFoundException;
import ua.nure.andrii.yahniukov.exception.station.StationAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.station.StationNotFoundException;
import ua.nure.andrii.yahniukov.exception.user.ForbiddenException;
import ua.nure.andrii.yahniukov.exception.user.UnauthorizedException;
import ua.nure.andrii.yahniukov.exception.user.UserAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.user.UserNotFoundException;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleUserNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "User not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleUserAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.value(), "User already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ChargerNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleChargerNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Charger not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChargerAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleChargerAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.value(), "Charger already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StationNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleServiceNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Station not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StationAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleStationAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.value(), "Station already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CarNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleCarNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Car not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleCarAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.value(), "Car already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ComplaintNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleComplaintNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Complaint not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorMessage> handleUnauthorizedException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Unauthorized user", new Date().getTime()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ErrorMessage> handleForbiddenExceptionException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.FORBIDDEN.value(), "Insufficient user rights", new Date().getTime()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorMessage> handleBadRequestException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Something was wrong", new Date().getTime()), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class ErrorMessage {
        private Integer status;
        private String message;
        private Long timestamp;
    }
}