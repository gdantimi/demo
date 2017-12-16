package com.gdantimi.demo.controller.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestError {
    private String message;
    private List<ErrorDetail> errors;
}
