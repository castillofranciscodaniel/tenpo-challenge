package com.org.tenpo.challenge.infraestructure.delivery;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import com.org.tenpo.challenge.infraestructure.delivery.dto.CalculateRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RestController("calculate")
public class CalculateController {

    private final CalculateCU calculateCU;

    private final FindPaginatedRequestLogCU findPaginatedRequestLogCU;

    public CalculateController(CalculateCU calculateCU, FindPaginatedRequestLogCU findPaginatedRequestLogCU) {
        this.calculateCU = calculateCU;
        this.findPaginatedRequestLogCU = findPaginatedRequestLogCU;
    }

    @PostMapping
    public Mono<Double> calculate(@RequestBody CalculateRequest calculateRequest) {
        return this.calculateCU.execute(calculateRequest.getNumberA(), calculateRequest.getNumberB());
    }

    @GetMapping("history")
    public Mono<SimplePage<RequestLog>> findPaginatedRequestLog(@RequestParam Integer page, @RequestParam Integer size) {
        return this.findPaginatedRequestLogCU.execute(page, size);
    }
}
