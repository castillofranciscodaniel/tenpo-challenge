package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.ExternalValue;
import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.port.ExternalInformationCacheRepository;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Date;

public class CalculateService {

    private final ExternalInformationRepository externalInformationRepository;

    private final ExternalInformationCacheRepository externalInformationCacheRepository;

    private final RequestLogRepository requestLogRepository;

    private static final int MAX_RETRIES = 3;

    public CalculateService(ExternalInformationRepository externalInformationRepository,
                            ExternalInformationCacheRepository externalInformationCacheRepository, RequestLogRepository requestLogRepository) {
        this.externalInformationRepository = externalInformationRepository;
        this.externalInformationCacheRepository = externalInformationCacheRepository;
        this.requestLogRepository = requestLogRepository;
    }

    public Mono<ExternalValue> findPercentage() {
        return this.externalInformationCacheRepository.findPercentage()
                .switchIfEmpty(this.findExternalAndSaveInCache())
                .flatMap(externalValue -> {

                    Mono<ExternalValue> defaultReturn = Mono.just(externalValue);

                    if (this.wasSavedMoreThanThirtyMinutosAgo(externalValue.getCreatedAt())) {
                        return this.findExternalAndSaveInCache().onErrorResume(error -> {
                            System.out.println("Max retries reached or unrecoverable error: " + error.getMessage());
                            return defaultReturn;  // Valor de fallback en caso de error
                        });
                    }

                    return defaultReturn;
                });
    }

    private Mono<ExternalValue> findExternalAndSaveInCache() {
        return this.externalInformationRepository.findPercentage()
                .flatMap(percentage -> this.externalInformationCacheRepository.savePercentage(percentage)
                        .map(x -> percentage))
                .retryWhen(Retry.fixedDelay(MAX_RETRIES, Duration.ofSeconds(1)));  // Reintentar 3 veces con intervalo de 1 segundo
    }

    private boolean wasSavedMoreThanThirtyMinutosAgo(Date dateSaved) {
        long millisecondsDifference = new Date().getTime() - dateSaved.getTime();
        long minutesDifference = millisecondsDifference / (60 * 1000);
        return minutesDifference > 30;
    }

    public void saveAsyncRequestLog(RequestLog requestLog) {
        this.requestLogRepository.save(requestLog).subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

}
