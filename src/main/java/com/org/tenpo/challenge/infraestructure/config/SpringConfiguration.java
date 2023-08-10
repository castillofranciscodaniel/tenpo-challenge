package com.org.tenpo.challenge.infraestructure.config;

import com.org.tenpo.challenge.core.model.ExternalValue;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
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
    public CalculateCU newCalculateCU(RequestLogRepository requestLogRepository, ExternalInformationRepository externalInformationRepository) {
        return new CalculateCU(requestLogRepository, externalInformationRepository);
    }

    @Bean
    public ReactiveRedisOperations<String, ExternalValue> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<ExternalValue> serializer = new Jackson2JsonRedisSerializer<>(ExternalValue.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, ExternalValue> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, ExternalValue> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
