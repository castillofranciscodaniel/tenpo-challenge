package com.org.tenpo.challenge.infraestructure.delivery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/percentage")
public class PercentageMockRequest {

    private final Random random;

    public PercentageMockRequest() {
        this.random = new Random();
    }

    @GetMapping
    public Integer findPaginatedRequestLog() {
        return random.nextInt(100) + 1;
    }
}
