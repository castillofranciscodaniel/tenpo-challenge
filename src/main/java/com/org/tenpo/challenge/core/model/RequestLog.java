package com.org.tenpo.challenge.core.model;

import java.util.Date;
import java.util.UUID;

public class RequestLog {

    private String id;
    private Double requestNumberA;

    private Double requestNumberB;

    private Double result;

    private final Date createdAt = new Date();

    public RequestLog() {
        this.id = UUID.randomUUID().toString();
    }

    public RequestLog(Double requestNumberA, Double requestNumberB, Double result) {
        this.id = UUID.randomUUID().toString();
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRequestNumberA() {
        return requestNumberA;
    }

    public void setRequestNumberA(Double requestNumberA) {
        this.requestNumberA = requestNumberA;
    }

    public Double getRequestNumberB() {
        return requestNumberB;
    }

    public void setRequestNumberB(Double requestNumberB) {
        this.requestNumberB = requestNumberB;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
