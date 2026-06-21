package com.yobrunox.tp01backendhwt.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Error de validación"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
public class ControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> duplicateResource(BusinessException e, WebRequest request){
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorDTO(e.getMessage(),e.getReasons()));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        List<String> reasons = new ArrayList<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            reasons.add(String.format("%s - %s", error.getField(), error.getDefaultMessage()));
        }
        return ResponseEntity.status(status)
                .body(new ErrorDTO( "Existen atributos con valores incorrectos", reasons));
    }

}
