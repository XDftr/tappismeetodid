package cc.cryptek.tappismeetodid.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        var responseStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), responseStatus), responseStatus);
    }

    @ExceptionHandler(OpenAiException.class)
    public ResponseEntity<ExceptionResponse> handleOpenAiException(OpenAiException ex) {
        var responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), responseStatus), responseStatus);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ExceptionResponse> handleTimeoutException(TimeoutException ex) {
        var responseStatus = HttpStatus.TOO_MANY_REQUESTS;
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), responseStatus), responseStatus);
    }

    @Getter
    @Setter
    static class ExceptionResponse {
        private final String message;
        private final int statusCode;
        private final String reason;
        private final LocalDateTime timestamp;

        public ExceptionResponse(String message, HttpStatus httpStatus) {
            this.message = message;
            this.statusCode = httpStatus.value();
            this.reason = httpStatus.getReasonPhrase();
            this.timestamp = LocalDateTime.now();
        }
    }
}
