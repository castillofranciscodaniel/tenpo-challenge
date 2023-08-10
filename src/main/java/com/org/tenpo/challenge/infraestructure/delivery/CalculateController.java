package com.org.tenpo.challenge.infraestructure.delivery;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import com.org.tenpo.challenge.infraestructure.delivery.dto.CalculateRequest;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController()
@RequestMapping("/calculate")
public class CalculateController {

    private final CalculateCU calculateCU;

    private final FindPaginatedRequestLogCU findPaginatedRequestLogCU;

    private final Bucket bucket;

    public CalculateController(CalculateCU calculateCU, FindPaginatedRequestLogCU findPaginatedRequestLogCU) {
        this.calculateCU = calculateCU;
        this.findPaginatedRequestLogCU = findPaginatedRequestLogCU;

        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));

        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping
    public Mono<Map<String, Double>> calculate(@RequestBody CalculateRequest calculateRequest) {
        return this.calculateCU.execute(calculateRequest.getNumberA(), calculateRequest.getNumberB()).map(result ->
                Map.of("result", result)
        );
    }

    @GetMapping("/history")
    public Mono<SimplePage<RequestLog>> findPaginatedRequestLog(@RequestParam Integer page, @RequestParam Integer size) {
        return this.findPaginatedRequestLogCU.execute(page, size);
    }

}
