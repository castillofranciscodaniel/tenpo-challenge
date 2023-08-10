package com.org.tenpo.challenge.infraestructure.delivery;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Controller
@RestController("percentaje")
public class PercentajeMockRequest {

    @GetMapping
    public Mono<Integer> findPaginatedRequestLog() {
        return ;
    }}
