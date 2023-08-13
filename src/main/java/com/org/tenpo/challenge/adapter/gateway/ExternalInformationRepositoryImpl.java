package com.org.tenpo.challenge.adapter.gateway;

import com.org.tenpo.challenge.core.exeption.FindingExternalValueException;
import com.org.tenpo.challenge.core.model.ExternalValue;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ExternalInformationRepositoryImpl implements ExternalInformationRepository {

    private final WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(ExternalInformationRepositoryImpl.class);

    public ExternalInformationRepositoryImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<ExternalValue> findPercentage() {
        logger.info("findPercentage end.");

        return webClient.get()
                .uri("http://localhost:8080/percentage")
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        response -> Mono.error(
                                new FindingExternalValueException(response.statusCode(), "error getting external percentage")
                        )
                )
                .bodyToMono(Double.class)
                .map(ExternalValue::new);
    }
}
