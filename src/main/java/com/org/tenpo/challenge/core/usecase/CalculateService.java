package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.port.ExternalInformationCacheRepository;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Date;

public class CalculateService {

    private final ExternalInformationRepository externalInformationRepository;

    private final ExternalInformationCacheRepository externalInformationCacheRepository;

    private static final int MAX_RETRIES = 3;

    public CalculateService(ExternalInformationRepository externalInformationRepository, ExternalInformationCacheRepository externalInformationCacheRepository) {
        this.externalInformationRepository = externalInformationRepository;
        this.externalInformationCacheRepository = externalInformationCacheRepository;
    }

    public Mono<Double> findPercentage() {
        return this.externalInformationCacheRepository.findPercentage().flatMap(externalValue -> {

            Mono<Double> defaultReturn = Mono.just(externalValue.getPercentage());

            if (this.wasSavedMoreThanThirtyMinutosAgo(externalValue.getCreatedAt())) {
                return this.findExternalAndSaveInCache().onErrorResume(error -> {
                    System.out.println("Max retries reached or unrecoverable error: " + error.getMessage());
                    return defaultReturn;  // Valor de fallback en caso de error
                });
            }

            return defaultReturn;
        }).switchIfEmpty(this.findExternalAndSaveInCache());
    }

    private Mono<Double> findExternalAndSaveInCache() {
        return this.externalInformationRepository.findPercentage()
                .flatMap(percentage -> this.externalInformationCacheRepository.savePercentage(percentage)
                        .filter(Boolean::booleanValue)
                        .map(x -> percentage)
                )
                .retryWhen(Retry.fixedDelay(MAX_RETRIES, Duration.ofSeconds(1)));  // Reintentar 3 veces con intervalo de 1 segundo

    }

    private boolean wasSavedMoreThanThirtyMinutosAgo(Date dateSaved) {
        long millisecondsDifference = new Date().getTime() - dateSaved.getTime();
        long minutesDifference = millisecondsDifference / (60 * 1000);
        return minutesDifference > 30;
    }

}
