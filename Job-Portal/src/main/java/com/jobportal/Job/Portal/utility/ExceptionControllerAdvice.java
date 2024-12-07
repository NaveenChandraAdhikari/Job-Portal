package com.jobportal.Job.Portal.utility;


import com.jobportal.Job.Portal.exception.JobPortalException;
import com.sun.net.httpserver.HttpsServer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


//handle exception in restapis
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /*
    The Environment is used to fetch application-specific error messages from the application.properties or application.yml file. This allows for centralized, configurable, and potentially localized error messages (e.g., USER_NOT_FOUND can map to a user-friendly message). It improves flexibility by decoupling exception handling logic from hardcoded messages.
     */
    @Autowired
    private Environment environment;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo>generalException(Exception exception){

        ErrorInfo error=new ErrorInfo(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }


//customized and application-specific response

    @ExceptionHandler(JobPortalException.class)
    public ResponseEntity<ErrorInfo>generalException(JobPortalException exception){

        String msg= environment.getProperty(exception.getMessage());

        ErrorInfo error=new ErrorInfo(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception){


        String msg="";

        if(exception instanceof MethodArgumentNotValidException methodArgumentNotValidException){

            msg=methodArgumentNotValidException.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        }

        else{
            ConstraintViolationException cvException=(ConstraintViolationException) exception;
            msg=cvException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        }



        ErrorInfo error=new ErrorInfo(msg, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);




    }


}
