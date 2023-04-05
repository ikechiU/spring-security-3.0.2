package com.example.demo_security.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author Ikechi Ucheagwu
 * @created 03/04/2023 - 23:11
 * @project demo_security
 */

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class DefaultController {

    @Operation(hidden = true)
    @GetMapping
    ResponseEntity<Void> redirect() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("swagger-ui/index.html"))
                .build();
    }
}
