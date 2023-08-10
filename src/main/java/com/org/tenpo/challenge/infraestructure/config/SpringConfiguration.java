package com.org.tenpo.challenge.infraestructure.config;

import com.org.tenpo.challenge.core.model.ExternalValue;
import com.org.tenpo.challenge.core.port.ExternalInformationCacheRepository;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.CalculateService;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableAutoConfiguration
public class SpringConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public FindPaginatedRequestLogCU newFindPaginatedRequestLogCU(RequestLogRepository requestLogRepository) {
        return new FindPaginatedRequestLogCU(requestLogRepository);
    }

    @Bean
    public CalculateService newCalculateService(ExternalInformationRepository externalInformationRepository,
                                                ExternalInformationCacheRepository externalInformationCacheRepository) {
        return new CalculateService(externalInformationRepository, externalInformationCacheRepository);
    }

    @Bean
    public CalculateCU newCalculateCU(RequestLogRepository requestLogRepository, CalculateService calculateService) {
        return new CalculateCU(requestLogRepository, calculateService);
    }

}
