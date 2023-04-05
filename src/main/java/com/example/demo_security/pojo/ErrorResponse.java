package com.example.demo_security.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 17:51
 * @project demo_security
 */

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private Collection message;
}
