package ua.nure.andrii.yahniukov.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.nure.andrii.yahniukov.exceptions.charger.ChargerAlreadyExistsException;
import ua.nure.andrii.yahniukov.exceptions.charger.ChargerNotFoundException;
import ua.nure.andrii.yahniukov.exceptions.complaint.ComplaintNotFoundException;
import ua.nure.andrii.yahniukov.exceptions.iot.CarAlreadyExistsException;
import ua.nure.andrii.yahniukov.exceptions.iot.CarNotFoundException;
import ua.nure.andrii.yahniukov.exceptions.station.StationAlreadyExistsException;
import ua.nure.andrii.yahniukov.exceptions.station.StationNotFoundException;
import ua.nure.andrii.yahniukov.exceptions.user.ForbiddenException;
import ua.nure.andrii.yahniukov.exceptions.user.UnauthorizedException;
import ua.nure.andrii.yahniukov.exceptions.user.UserAlreadyExistsException;
import ua.nure.andrii.yahniukov.exceptions.user.UserNotFoundException;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleUserNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "User not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleUserAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT, "User already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ChargerNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleChargerNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Charger not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChargerAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleChargerAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT, "Charger already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StationNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleServiceNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Station not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StationAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleStationAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT, "Station already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CarNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleCarNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Car not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleCarAlreadyExistsException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT, "Car already exists", new Date().getTime()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ComplaintNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleComplaintNotFoundException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Complaint not found", new Date().getTime()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorMessage> handleUnauthorizedException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.UNAUTHORIZED, "Unauthorized user", new Date().getTime()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ErrorMessage> handleForbiddenExceptionException() {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.FORBIDDEN, "Insufficient user rights", new Date().getTime()), HttpStatus.FORBIDDEN);
    }

    @Data
    @AllArgsConstructor
    private static class ErrorMessage {
        private HttpStatus status;
        private String message;
        private Long timestamp;
    }
}