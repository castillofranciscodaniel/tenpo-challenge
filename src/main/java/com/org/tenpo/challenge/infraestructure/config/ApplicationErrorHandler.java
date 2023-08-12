package com.org.tenpo.challenge.infraestructure.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e){
        var errorResponse = this.buildErrorResponse(417, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

//    @ExceptionHandler(StudentNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException e){
//        var errorResponse = this.buildErrorResponse(101, String.format("Student id %s is not found", e.getStudentId()));
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(errorResponse);
//    }

    private ErrorResponse buildErrorResponse(int code, String message){
        return new ErrorResponse(code, message);
    }

}