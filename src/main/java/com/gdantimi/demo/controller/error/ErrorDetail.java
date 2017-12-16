package com.gdantimi.demo.controller.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetail {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;

}
