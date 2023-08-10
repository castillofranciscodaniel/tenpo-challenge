package com.org.tenpo.challenge.adapter.gateway;

import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ExternalInformationRepositoryImpl implements ExternalInformationRepository {

    private final WebClient webClient;

    public ExternalInformationRepositoryImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Double> findPercentage() {
        return webClient.get()
                .uri("/percentage")
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), response -> Mono.error(new RuntimeException("Error when calling")))
                .bodyToMono(Double.class);
    }
}
