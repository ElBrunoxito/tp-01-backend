package com.yobrunox.tp01backendhwt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusinessException extends RuntimeException{
    private HttpStatus status;
    private String message;

    private List<String> reasons;


    public BusinessException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }
}
