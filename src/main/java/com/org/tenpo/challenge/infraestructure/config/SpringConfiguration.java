package com.org.tenpo.challenge.infraestructure.config;

import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import com.org.tenpo.challenge.core.usecase.CalculateCU;
import com.org.tenpo.challenge.core.usecase.FindPaginatedRequestLogCU;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class SpringConfiguration {

    @Bean
    public FindPaginatedRequestLogCU newFindPaginatedRequestLogCU(RequestLogRepository requestLogRepository) {
        return new FindPaginatedRequestLogCU(requestLogRepository);
    }

    @Bean
    public CalculateCU newCalculateCU(RequestLogRepository requestLogRepository, ExternalInformationRepository externalInformationRepository) {
        return new CalculateCU(requestLogRepository, externalInformationRepository);
    }

}
