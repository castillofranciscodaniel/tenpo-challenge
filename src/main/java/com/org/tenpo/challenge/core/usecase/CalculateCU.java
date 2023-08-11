package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.model.RequestLogState;
import reactor.core.publisher.Mono;

public class CalculateCU {

    private final CalculateService calculateService;

    public CalculateCU(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    // TODO: falta guardar los casos no exitosos
    public Mono<Double> execute(Double numberA, Double numberB) {

        double sum = numberA + numberB;

        return this.calculateService.findPercentage().map(percentage -> {
            double result = sum + (sum * percentage / 100);

            // utilizamos subscribeOn(Schedulers.boundedElastic()) para especificar que queremos que esta
            // tarea se ejecute en un grupo de subprocesos elástico (con capacidad limitada) en segundo plano.

            RequestLog requestLog = new RequestLog(numberA, numberB, result, RequestLogState.SUCCESSFUL);

            this.calculateService.saveAsyncRequestLog(requestLog);

            return result;
        }).doOnError(error -> {
            RequestLog requestLog = new RequestLog(numberA, numberB, RequestLogState.ERROR);
            this.calculateService.saveAsyncRequestLog(requestLog);
        });
    }

}

