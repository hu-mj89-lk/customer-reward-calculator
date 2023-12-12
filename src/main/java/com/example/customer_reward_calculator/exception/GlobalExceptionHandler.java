package com.example.customer_reward_calculator.exception;

import com.example.customer_reward_calculator.api.error.ErrorRS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex,
                                                    WebRequest request) {
        log.error(ExceptionUtils.getStackTrace(ex));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal server error";

        if (ex instanceof BadRequestException brEx) {
            httpStatus = brEx.getHttpStatus();
            message = brEx.getMessage();
        } else if (ex instanceof UnprocessableEntityException ueEx) {
            httpStatus = ueEx.getHttpStatus();
            message = ueEx.getMessage();
        } else if (ex instanceof NoSuchElementException nseEx) {
            message = nseEx.getMessage();
        }

        ErrorRS errorRS = ErrorRS.builder()
                .status(httpStatus.value())
                .message(message)
                .build();

        return handleExceptionInternal(ex, errorRS, httpHeaders, httpStatus, request);
    }

}
