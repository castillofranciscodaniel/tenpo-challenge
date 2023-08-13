package com.org.tenpo.challenge.core.port;

import com.org.tenpo.challenge.core.model.ExternalValue;
import reactor.core.publisher.Mono;

public interface ExternalInformationRepository {

    Mono<ExternalValue> findPercentage();

}
