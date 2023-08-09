package com.org.tenpo.challenge.core.model;

import java.util.Date;

public class RequestLog {

    private Double requestNumberA;

    private Double requestNumberB;

    private Double result;

    private final Date createdAt = new Date();

    public RequestLog() {
    }

    public RequestLog(Double requestNumberA, Double requestNumberB, Double result) {
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
        this.result = result;
    }

    public Double getRequestNumberA() {
        return requestNumberA;
    }

    public Double getRequestNumberB() {
        return requestNumberB;
    }

    public Double getResult() {
        return result;
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

    public void setResult(Double result) {
        this.result = result;
    }
}
