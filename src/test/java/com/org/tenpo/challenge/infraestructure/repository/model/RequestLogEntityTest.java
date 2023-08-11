package com.org.tenpo.challenge.infraestructure.repository.model;

import com.org.tenpo.challenge.core.model.RequestLog;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class RequestLogEntityTest {

    @Test
    public void entityToModel_test() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse("2023-08-10 15:30:00");

        RequestLogEntity requestLogEntity = new RequestLogEntity(
                "1",
                5.0,
                5.0,
                10.0,
                LocalDateTime.of(2023, 8, 10, 15, 30, 0)
        );

        RequestLog requestLog = new RequestLog(
                "1",
                5.0,
                5.0,
                10.0,
                date
        );

        Assert.assertEquals(requestLogEntity, new RequestLogEntity(requestLog));

    }

}
