package com.kh.EcomercebackEndspringboot.Exception;

import com.kh.EcomercebackEndspringboot.Entity.DTO.ErrorResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler_ {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        StringBuilder message = new StringBuilder();
        BindingResult bindingResult = ex.getBindingResult();
        for(FieldError err:bindingResult.getFieldErrors()){
            message.append(err.getField())
                    .append(": ")
                    .append(err.getDefaultMessage())
                    .append("; ");
        }
        return ResponseEntity.badRequest().body(
                new ErrorResponse("400",message.toString())
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptionMethod(Exception ex){
        String message="" ;
        if(ex instanceof ChangeSetPersister.NotFoundException){
            message = "Not Found Exception was thrown\n"+ex.getMessage() ;
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("404",message)
            );
        }
        ErrorResponse errorResponse = new ErrorResponse() ;
        errorResponse.setStatus_code(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse) ;
    }
}
