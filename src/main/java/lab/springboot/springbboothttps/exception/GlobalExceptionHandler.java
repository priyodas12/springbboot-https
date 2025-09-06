package lab.springboot.springbboothttps.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionContext> handle401Exception(AuthenticationException e, HttpServletRequest req) {
        log.warn("AuthenticationException : {}, timestamp: {}", e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionContext.builder()
                        .path(req.getRequestURI())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(e.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ExceptionContext> handle403Exception(AccessDeniedException e, HttpServletRequest req) {
        log.warn("AuthorizationDeniedException : {}, timestamp: {}", e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ExceptionContext.builder()
                        .path(req.getRequestURI())
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(e.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionContext> handleNotImplementedException(UnsupportedOperationException e, HttpServletRequest req) {
        log.warn("UnsupportedOperationException : {}, timestamp: {}", e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(ExceptionContext.builder()
                        .path(req.getRequestURI())
                        .status(HttpStatus.NOT_IMPLEMENTED.value())
                        .message(e.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionContext> handleSQLException(DataIntegrityViolationException e, HttpServletRequest req) {
        log.warn("DataIntegrityViolationException : {}, timestamp: {}", e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionContext.builder()
                        .path(req.getRequestURI())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(e.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionContext> handleGenericException(Exception e, HttpServletRequest req) {
        log.warn("Exception : {}, timestamp: {}", e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(SERVICE_UNAVAILABLE)
                .body(ExceptionContext.builder()
                        .path(req.getRequestURI())
                        .status(SERVICE_UNAVAILABLE.value())
                        .message(e.getMessage())
                        .timestamp(Instant.now())
                        .build());
    }
}
