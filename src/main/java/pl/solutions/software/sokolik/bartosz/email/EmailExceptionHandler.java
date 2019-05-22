package pl.solutions.software.sokolik.bartosz.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.solutions.software.sokolik.bartosz.email.dto.ApiError;
import pl.solutions.software.sokolik.bartosz.email.exception.InvalidEmailDateException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
class EmailExceptionHandler {

    @ExceptionHandler(InvalidEmailDateException.class)
    ResponseEntity<ApiError> handleMessagingException(final HttpServletRequest request, final Exception exception) {
        ApiError apiError = createApiError(HttpStatus.BAD_REQUEST, exception, request);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    private ApiError createApiError(HttpStatus status, Exception exception, HttpServletRequest request) {
        return ApiError.builder()
                .status(status.value())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();
    }
}
