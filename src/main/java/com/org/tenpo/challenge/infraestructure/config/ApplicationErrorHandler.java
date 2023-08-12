package com.org.tenpo.challenge.infraestructure.config;

import com.org.tenpo.challenge.core.exeption.CoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CoreException> handleRuntimeException(RuntimeException e) {
        var errorResponse = new CoreException(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<CoreException> handleStudentNotFoundException(CoreException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e);
    }

}