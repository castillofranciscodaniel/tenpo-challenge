package com.org.tenpo.challenge.core.model;

import java.util.Date;

public class RequestLog {

    private Long requestNumberA;

    private Long requestNumberB;

    private Long response;

    private final Date createdAt = new Date();

    public RequestLog() {
    }

    public RequestLog(Long requestNumberA, Long requestNumberB) {
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
    }

    public Long getRequestNumberA() {
        return requestNumberA;
    }

    public Long getRequestNumberB() {
        return requestNumberB;
    }

    public Long getResponse() {
        return response;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setRequestNumberA(Long requestNumberA) {
        this.requestNumberA = requestNumberA;
    }

    public void setRequestNumberB(Long requestNumberB) {
        this.requestNumberB = requestNumberB;
    }

    public void setResponse(Long response) {
        this.response = response;
    }
}
