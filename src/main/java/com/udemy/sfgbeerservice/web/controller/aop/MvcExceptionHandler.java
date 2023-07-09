package com.udemy.sfgbeerservice.web.controller.aop;

import com.udemy.sfgbeerservice.web.controller.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
/**
 * The@ControllerAdvice annotation allows us to consolidate our multiple,
 * scattered @ExceptionHandlers from before into a single, global error handling component.
 * The actual mechanism is extremely simple but also very flexible:
 * It gives us full control over the body of the response as well as the status code.
 */
@Slf4j
@ControllerAdvice
public class MvcExceptionHandler {

    // If there is a problem with one of the parameters, we send Bad Request response
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException exc) {
        List<String> errors = new ArrayList<>(exc.getConstraintViolations().size());

        exc.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> validationErrorHandler(NotFoundException exc) {

        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }


}