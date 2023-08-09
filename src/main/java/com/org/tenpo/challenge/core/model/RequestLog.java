package com.org.tenpo.challenge.core.model;

import java.util.Date;

public class RequestLog {

    private Double requestNumberA;

    private Double requestNumberB;

    private Double response;

    private final Date createdAt = new Date();

    public RequestLog() {
    }

    public RequestLog(Double requestNumberA, Double requestNumberB) {
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
    }

    public Double getRequestNumberA() {
        return requestNumberA;
    }

    public Double getRequestNumberB() {
        return requestNumberB;
    }

    public Double getResponse() {
        return response;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setRequestNumberA(Double requestNumberA) {
        this.requestNumberA = requestNumberA;
    }

    public void setRequestNumberB(Double requestNumberB) {
        this.requestNumberB = requestNumberB;
    }

    public void setResponse(Double response) {
        this.response = response;
    }
}
