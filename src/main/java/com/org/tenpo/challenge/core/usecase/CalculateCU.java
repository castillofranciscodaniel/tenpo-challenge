package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

public class CalculateCU {

    private final RequestLogRepository requestLogRepository;

    private final ExternalInformationRepository externalInformationRepository;

    public CalculateCU(RequestLogRepository requestLogRepository, ExternalInformationRepository externalInformationRepository) {
        this.requestLogRepository = requestLogRepository;
        this.externalInformationRepository = externalInformationRepository;
    }

    public Mono<Double> execute(Double numberA, Double numberB) {
        int maxRetries = 3;

        double sum = numberA + numberB;

        return this.externalInformationRepository.findPercentage().map(percentage -> {
                    double result = sum + (sum * percentage / 100);

                    // utilizamos subscribeOn(Schedulers.boundedElastic()) para especificar que queremos que esta
                    // tarea se ejecute en un grupo de subprocesos elástico (con capacidad limitada) en segundo plano.

                    RequestLog requestLog = new RequestLog(numberA, numberB, result);
                    this.requestLogRepository.save(requestLog).subscribeOn(Schedulers.boundedElastic())
                            .subscribe();

                    return result;
                })
                .retryWhen(Retry.fixedDelay(maxRetries, Duration.ofSeconds(1)))  // Reintentar 3 veces con intervalo de 1 segundo
                .onErrorResume(error -> {
                    System.out.println("Max retries reached or unrecoverable error: " + error.getMessage());
                    return Mono.just(-1.0);  // Valor de fallback en caso de error
                });
    }
}
