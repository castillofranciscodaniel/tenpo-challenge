package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
import com.org.tenpo.challenge.core.port.ExternalInformationCacheRepository;
import com.org.tenpo.challenge.core.port.ExternalInformationRepository;
import com.org.tenpo.challenge.core.port.RequestLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateServiceTest {

    @MockBean
    private ExternalInformationRepository externalInformationRepository;

    @MockBean
    private ExternalInformationCacheRepository externalInformationCacheRepository;

    @Autowired
    private CalculateService calculateService;

    @Test
    public void find_percentage_from_with_empty_cache_Test() {


        when(externalInformationCacheRepository.findPercentage()).thenReturn(Mono.empty());
        when(externalInformationCacheRepository.savePercentage(10.0)).thenReturn(Mono.just(true));
        when(externalInformationRepository.findPercentage()).thenReturn(Mono.just(10.0));

        StepVerifier.create(calculateService.findPercentage())
                .expectNext(10.0)
                .verifyComplete();

        verify(externalInformationCacheRepository, times(1)).findPercentage();
        verify(externalInformationRepository, times(1)).findPercentage();
    }
}
