package io.hrushik09.ecommerce.inventory.web.exceptions;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationAlreadyExists;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseAlreadyExists;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String SERVICE_NAME = "inventory-service";

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Invalid request payload");
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("timestamp", Instant.now());
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        problemDetail.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler({LocationAlreadyExists.class, WarehouseAlreadyExists.class})
    ProblemDetail handleLocationAlreadyExists(AlreadyExists e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(LocationDoesNotExist.class)
    ProblemDetail handleLocationDoesNotExist(LocationDoesNotExist e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
