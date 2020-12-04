package com.hti.pos.controller;

import com.hti.pos.dto.FieldDTO;
import com.hti.pos.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 1:42 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Slf4j
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        FieldError fieldError = ex.getBindingResult().getFieldError();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(status.toString())
                .message(fieldError.getDefaultMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<ResponseDTO> handleTransactionException(TransactionSystemException ex) throws Throwable {
        Throwable cause = ex.getCause();
        if (!(cause instanceof RollbackException)) {
            throw cause;
        }
        if (!(cause.getCause() instanceof ConstraintViolationException)) {
            throw cause.getCause();
        }
        ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();
        List<FieldDTO> errorMessage = validationException.getConstraintViolations().stream()
                .map(item -> FieldDTO.builder()
                        .field(String.valueOf(item.getPropertyPath()))
                        .message(item.getMessage()).build()
                ).collect(Collectors.toList());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(errorMessage).build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
