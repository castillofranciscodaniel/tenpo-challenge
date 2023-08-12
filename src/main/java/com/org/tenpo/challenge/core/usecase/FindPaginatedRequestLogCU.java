package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.SimplePage;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class FindPaginatedRequestLogCU {

    private static final Logger logger = LoggerFactory.getLogger(FindPaginatedRequestLogCU.class);

    private final RequestLogRepository requestLogRepository;

    public FindPaginatedRequestLogCU(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public Mono<SimplePage<RequestLog>> execute(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) page = 10;

        logger.info("execute init. page: {}, size: {}", page, size);
        return this.requestLogRepository.find(page, size);
    }
}
