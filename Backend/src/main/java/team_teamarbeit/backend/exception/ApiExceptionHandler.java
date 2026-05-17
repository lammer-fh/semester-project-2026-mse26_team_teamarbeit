package team_teamarbeit.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import team_teamarbeit.backend.dto.ErrorDto;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(ApiException exception) {
        return ResponseEntity
            .status(exception.getStatus())
            .body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleMalformedBody(HttpMessageNotReadableException exception) {
        return ResponseEntity
            .badRequest()
            .body(new ErrorDto("The request body is malformed."));
    }

    @ExceptionHandler({
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorDto> handleBadRequest(Exception exception) {
        return ResponseEntity
            .badRequest()
            .body(new ErrorDto("The request parameters are invalid."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnexpected(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorDto("Something went wrong, please contact our service-desk."));
    }
}
