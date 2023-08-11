package com.org.tenpo.challenge.infraestructure.delivery;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import com.org.tenpo.challenge.infraestructure.delivery.dto.CalculateRequest;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<Map<String, Double>>> calculate(@RequestBody CalculateRequest calculateRequest) {
        if (bucket.tryConsume(1)) {
            return this.calculateCU.execute(calculateRequest.getNumberA(), calculateRequest.getNumberB()).map(result ->
                    ResponseEntity.ok(Map.of("result", result))
            );
        }

        return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build());
    }

    @GetMapping("/history")
    public Mono<ResponseEntity<SimplePage<RequestLog>>> findPaginatedRequestLog(@RequestParam Integer page, @RequestParam Integer size) {
        if (bucket.tryConsume(1)) {
            return this.findPaginatedRequestLogCU.execute(page, size).map(ResponseEntity::ok);
        }

        return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build());
    }

}

// TODO: mapear errores generales para 4xx
// TODO: hacer test de ports
// TODO: dockerhub público
// TODO: Postman Collection o Swagger
// TODO: Readme