package com.bistro.booking.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        return new ResponseEntity<>(jsonify(ex), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleAgeNotAllowedException(InvalidFormatException ex, HttpServletRequest request) {
        return new ResponseEntity<>(jsonify(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        return new ResponseEntity<>(jsonify(new Exception("JWT token expired")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(jsonify(ex), HttpStatus.BAD_REQUEST);
    }
    
    private Map<String,String> jsonify(Exception ex){
        System.out.println("ex.getMessage() = " + ex.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("error",ex.getMessage());
        return map;
    }
}
