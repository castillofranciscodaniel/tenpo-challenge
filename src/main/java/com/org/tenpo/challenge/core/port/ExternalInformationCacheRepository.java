package com.org.tenpo.challenge.core.port;

import com.org.tenpo.challenge.core.model.ExternalValue;
import reactor.core.publisher.Mono;

public interface ExternalInformationCacheRepository {

    Mono<ExternalValue> findPercentage();

    Mono<Boolean> savePercentage(Double percentage);

}
