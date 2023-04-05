package com.example.demo_security.pojo;

import lombok.Data;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 14:30
 * @project demo_security
 */

@Data
public class UserRequest {
    private String email;
    private String password;
}
