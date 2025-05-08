package com.example.liability_scheme.Response;

 

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A generic wrapper class for API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status; 
  	private int code;
    private String message; 
    @JsonInclude(JsonInclude.Include.NON_NULL)                   
    private T data;  
            
}

