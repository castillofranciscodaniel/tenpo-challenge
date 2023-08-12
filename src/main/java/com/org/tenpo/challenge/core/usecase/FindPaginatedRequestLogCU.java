package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import reactor.core.publisher.Mono;

public class FindPaginatedRequestLogCU {

    private final RequestLogRepository requestLogRepository;

    public FindPaginatedRequestLogCU(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public Mono<SimplePage<RequestLog>> execute(Integer page, Integer size) {

        return this.requestLogRepository.find(page, size);
    }
}
