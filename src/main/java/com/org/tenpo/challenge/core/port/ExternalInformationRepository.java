package com.org.tenpo.challenge.core.port;

import reactor.core.publisher.Mono;

public interface ExternalInformationRepository {

    Mono<Double> findPercentage();

}
