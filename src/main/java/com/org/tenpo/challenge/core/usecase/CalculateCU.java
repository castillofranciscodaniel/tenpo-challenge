package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class CalculateCU {

    private final RequestLogRepository requestLogRepository;

    private final CalculateService calculateService;

    public CalculateCU(RequestLogRepository requestLogRepository, CalculateService calculateService) {
        this.requestLogRepository = requestLogRepository;
        this.calculateService = calculateService;
    }

    public Mono<Double> execute(Double numberA, Double numberB) {

        double sum = numberA + numberB;

        return this.calculateService.findPercentage().map(percentage -> {
            double result = sum + (sum * percentage / 100);

            // utilizamos subscribeOn(Schedulers.boundedElastic()) para especificar que queremos que esta
            // tarea se ejecute en un grupo de subprocesos elástico (con capacidad limitada) en segundo plano.

            RequestLog requestLog = new RequestLog(numberA, numberB, result);

            this.requestLogRepository.save(requestLog).subscribeOn(Schedulers.boundedElastic())
                    .subscribe();

            return result;
        });
    }

}

