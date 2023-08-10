package com.org.tenpo.challenge.adapter.gateway;

import com.org.tenpo.challenge.core.model.ExternalValue;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class ExternalInformationRepositoryImpl implements ExternalInformationRepository {

    private final WebClient webClient;

    private final ReactiveRedisOperations<String, ExternalValue> redisOperations;

    private final String REDIS_VALUE = "external_value";

    public ExternalInformationRepositoryImpl(WebClient webClient, ReactiveRedisOperations<String, ExternalValue> redisOperations) {
        this.webClient = webClient;
        this.redisOperations = redisOperations;
    }

    @Override
    public Mono<Double> findPercentage() {

        return this.redisOperations.opsForValue().get(REDIS_VALUE)
                .filter(x -> this.wasSavedMoreThanThirtyMinutosAgo(x.getCreatedAt()))
                .map(ExternalValue::getPercentage)
                .switchIfEmpty(
                        webClient.get()
                                .uri("http://localhost:8080/percentage")
                                .retrieve()
                                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                                        response -> Mono.error(new RuntimeException("Error when calling")))
                                .bodyToMono(Double.class)
                );


    }

    private boolean wasSavedMoreThanThirtyMinutosAgo(Date dateSaved) {
        long millisecondsDifference = new Date().getTime() - dateSaved.getTime();
        long minutesDifference = millisecondsDifference / (60 * 1000);
        return minutesDifference > 30;
    }
}
