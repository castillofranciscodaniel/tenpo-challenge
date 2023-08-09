package com.org.tenpo.challenge.adapter.gateway;

import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ExternalInformationRepositoryImpl implements ExternalInformationRepository {
    @Override
    public Mono<Double> findPercentage() {
        return null;
    }
}
