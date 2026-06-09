package team_teamarbeit.backend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;
import team_teamarbeit.backend.dto.ErrorDto;
import team_teamarbeit.backend.exceptions.BadRequestException;
import team_teamarbeit.backend.exceptions.BookingConflictException;
import team_teamarbeit.backend.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationExceptions(BindException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ErrorDto(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequest(BadRequestException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMalformedBody() {
        return new ErrorDto("Malformed request body");
    }

    @ExceptionHandler(BookingConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleConflict(BookingConflictException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFound(ResourceNotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }
}
