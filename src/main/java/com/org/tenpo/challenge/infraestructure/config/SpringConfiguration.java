package com.org.tenpo.challenge.infraestructure.config;

import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.DatabasePopulator;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
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

}
