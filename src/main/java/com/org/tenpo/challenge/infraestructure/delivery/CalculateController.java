package com.org.tenpo.challenge.infraestructure.delivery;

import com.org.tenpo.challenge.core.exeption.RateLimitException;
import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import com.org.tenpo.challenge.infraestructure.delivery.dto.CalculateRequest;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController()
@RequestMapping("/calculate")
public class CalculateController {

    private static final Logger logger = LoggerFactory.getLogger(CalculateController.class);

    private final CalculateCU calculateCU;

    private final FindPaginatedRequestLogCU findPaginatedRequestLogCU;

    private final Bucket bucket;

    public CalculateController(CalculateCU calculateCU, FindPaginatedRequestLogCU findPaginatedRequestLogCU, Bucket bucket) {
        this.calculateCU = calculateCU;
        this.findPaginatedRequestLogCU = findPaginatedRequestLogCU;
        this.bucket = bucket;
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Double>>> calculate(@RequestBody CalculateRequest calculateRequest) {

        logger.info("calculate init. calculateRequest: {}", calculateRequest);

        logger.info("");
        if (bucket.tryConsume(1)) {
            return this.calculateCU.execute(calculateRequest.getNumberA(), calculateRequest.getNumberB()).map(result ->
                    ResponseEntity.ok(Map.of("result", result))
            );
        }

        logger.error("calculate end. To many request. calculateRequest: {}", calculateRequest);
        return Mono.error(new RateLimitException());
    }

    @GetMapping("/history")
    public Mono<ResponseEntity<SimplePage<RequestLog>>> findPaginatedRequestLog(@RequestParam Integer page, @RequestParam Integer size) {
        logger.info("findPaginatedRequestLog init. page: {}, size: {}", page, size);

        if (bucket.tryConsume(1)) {
            return this.findPaginatedRequestLogCU.execute(page, size)
                    .map(ResponseEntity::ok);
        }

        logger.error("findPaginatedRequestLog end. To many request. page: {}, size: {}", page, size);
        return Mono.error(new RateLimitException());
    }

}

// TODO: hacer test de ports
// TODO: dockerhub público
// TODO: Postman Collection o Swagger
// TODO: Readme