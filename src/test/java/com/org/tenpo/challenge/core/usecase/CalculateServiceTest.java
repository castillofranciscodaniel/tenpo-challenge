package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.ExternalValue;
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

import java.util.Calendar;

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
        verify(externalInformationCacheRepository, times(1)).savePercentage(10.0);
        verify(externalInformationRepository, times(1)).findPercentage();
    }

    @Test
    public void find_percentage_from_with_empty_cache_error_saving_Test() {


        when(externalInformationCacheRepository.findPercentage()).thenReturn(Mono.empty());
        when(externalInformationCacheRepository.savePercentage(10.0)).thenReturn(Mono.just(false));
        when(externalInformationRepository.findPercentage()).thenReturn(Mono.just(10.0));

        StepVerifier.create(calculateService.findPercentage())
                .expectNext(10.0)
                .verifyComplete();

        verify(externalInformationCacheRepository, times(1)).findPercentage();
        verify(externalInformationCacheRepository, times(1)).savePercentage(10.0);
        verify(externalInformationRepository, times(1)).findPercentage();
    }

    @Test
    public void find_percentage_from_with_cache_before_30_minutes_Test() {

        var calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -29);

        var externalValue = new ExternalValue(8.0, calendar.getTime());

        when(externalInformationCacheRepository.findPercentage()).thenReturn(Mono.just(externalValue));
        when(externalInformationRepository.findPercentage()).thenReturn(Mono.empty());

        StepVerifier.create(calculateService.findPercentage())
                .expectNext(8.0)
                .verifyComplete();

        verify(externalInformationCacheRepository, times(1)).findPercentage();
    }

    @Test
    public void find_percentage_from_with_cache_after_30_minutes_Test() {

        var calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -31);

        var externalValue = new ExternalValue(8.0, calendar.getTime());

        when(externalInformationCacheRepository.findPercentage()).thenReturn(Mono.just(externalValue));
        when(externalInformationRepository.findPercentage()).thenReturn(Mono.just(20.0));
        when(externalInformationCacheRepository.savePercentage(20.0)).thenReturn(Mono.just(true));


        StepVerifier.create(calculateService.findPercentage())
                .expectNext(20.0)
                .verifyComplete();

        verify(externalInformationCacheRepository, times(1)).findPercentage();
        verify(externalInformationCacheRepository, times(1)).savePercentage(any());
        verify(externalInformationRepository, times(2)).findPercentage();

    }

    @Test
    public void find_percentage_from_with_cache_after_30_minutes_error_saving_cache_Test() {

        var calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -31);

        var externalValue = new ExternalValue(8.0, calendar.getTime());

        when(externalInformationCacheRepository.findPercentage()).thenReturn(Mono.just(externalValue));
        when(externalInformationRepository.findPercentage()).thenReturn(Mono.just(20.0));
        when(externalInformationCacheRepository.savePercentage(20.0)).thenReturn(Mono.just(false));


        StepVerifier.create(calculateService.findPercentage())
                .expectNext(20.0)
                .verifyComplete();

        verify(externalInformationCacheRepository, times(1)).findPercentage();
        verify(externalInformationCacheRepository, times(1)).savePercentage(any());
        verify(externalInformationRepository, times(2)).findPercentage();

    }


}
