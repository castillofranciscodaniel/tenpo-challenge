package com.org.tenpo.challenge.core.usecase;

import com.org.tenpo.challenge.core.model.RequestLog;
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
public class CalculateCUTest {

    @MockBean
    private RequestLogRepository requestLogRepository;

    @MockBean
    private CalculateService calculateService;

    @Autowired
    private CalculateCU calculateCU;

    @Test
    public void execute_ok_Test() {
        var numberA = 5.0;
        var numberB = 10.0;

        var percentage = 10.0;
        var result = 16.5;

        var requestLog = new RequestLog(numberA, numberB, result);

        when(calculateService.findPercentage()).thenReturn(Mono.just(percentage));
        when(requestLogRepository.save(any())).thenReturn(Mono.just(requestLog));

        StepVerifier.create(calculateCU.execute(numberA, numberB))
                .expectNext(16.5)
                .verifyComplete();

        verify(calculateService, times(1)).findPercentage();
        verify(requestLogRepository, times(1)).save(any());
    }

    // TODO: hacer test para chequear que si el save falla, no impacte en el CU
}
