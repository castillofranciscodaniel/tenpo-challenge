package com.org.tenpo.challenge.infraestructure.delivery;

import com.org.tenpo.challenge.core.exeption.RateLimitException;
import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/requestLog")
public class RequestLogController {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogController.class);

    private final FindPaginatedRequestLogCU findPaginatedRequestLogCU;

    private final Bucket bucket;

    public RequestLogController(FindPaginatedRequestLogCU findPaginatedRequestLogCU, Bucket bucket) {
        this.findPaginatedRequestLogCU = findPaginatedRequestLogCU;
        this.bucket = bucket;
    }

    @GetMapping("/history")
    public Mono<ResponseEntity<SimplePage<RequestLog>>> findPaginatedRequestLog(@RequestParam Integer page, @RequestParam Integer size) {
        logger.info("findPaginatedRequestLog init. page: {}, size: {}", page, size);

        if (!bucket.tryConsume(1)) {
            logger.error("findPaginatedRequestLog end. To many request. page: {}, size: {}", page, size);
            return Mono.error(new RateLimitException());

        }

        return this.findPaginatedRequestLogCU.execute(page, size)
                .map(ResponseEntity::ok);
    }

}

// TODO: hacer test de ports
// TODO: dockerhub público
// TODO: Postman Collection o Swagger
// TODO: Readme