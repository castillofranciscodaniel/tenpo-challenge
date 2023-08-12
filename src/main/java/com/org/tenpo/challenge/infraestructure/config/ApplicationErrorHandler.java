package com.org.tenpo.challenge.infraestructure.config;

import com.org.tenpo.challenge.core.exeption.CoreException;
import com.org.tenpo.challenge.core.exeption.FindingExternalValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<CoreException> handleCoreException(CoreException e) {
        return ResponseEntity.status(e.getCode())
                .body(e);
    }

}