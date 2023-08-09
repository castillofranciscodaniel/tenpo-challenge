package com.org.tenpo.challenge.core.repository;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import reactor.core.publisher.Mono;

public interface RequestLogRepository {

    Mono<RequestLog> save();

    Mono<SimplePage<RequestLog>> find();

}
