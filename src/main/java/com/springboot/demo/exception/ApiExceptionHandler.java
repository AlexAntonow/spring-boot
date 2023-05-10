package com.springboot.demo.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    List<FieldExceptionMessage> fieldExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(fieldError -> new FieldExceptionMessage(fieldError.getField(),
                fieldError.getDefaultMessage())).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    ApiExceptionMessage validationException(ValidationException ex) {
        Date date = new Date();
        return new ApiExceptionMessage("Failed", ex.getMessage(), dateFormat.format(date));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    ApiExceptionMessage notFoundException(NotFoundException ex) {
        Date date = new Date();
        return new ApiExceptionMessage("Failed", ex.getMessage(), dateFormat.format(date));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    ApiExceptionMessage methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Date date = new Date();
        return new ApiExceptionMessage("Failed", "Wrong http method or routing", dateFormat.format(date));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    ApiExceptionMessage generalException(Exception ex) {
        Date date = new Date();
        log.info(ex.getMessage());
        log.info(Arrays.toString(ex.getStackTrace()));
        return new ApiExceptionMessage("Failed", "An exception occurred while processing request", dateFormat.format(date));
    }

}
