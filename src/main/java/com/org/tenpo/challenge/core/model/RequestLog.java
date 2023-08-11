package com.org.tenpo.challenge.core.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class RequestLog {

    private String id;

    private Double requestNumberA;

    private Double requestNumberB;

    private Double result;

    private Date createdAt;

    public RequestLog() {
        this.id = UUID.randomUUID().toString();
    }

    public RequestLog(Double requestNumberA, Double requestNumberB, Double result) {
        this.id = UUID.randomUUID().toString();
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
        this.result = result;
        this.createdAt = new Date();
    }

    public RequestLog(String id, Double requestNumberA, Double requestNumberB, Double result, Date createdAt) {
        this.id = id;
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
        this.result = result;
        this.createdAt = createdAt;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLog that = (RequestLog) o;
        return Objects.equals(id, that.id) && Objects.equals(requestNumberA, that.requestNumberA) && Objects.equals(requestNumberB, that.requestNumberB) && Objects.equals(result, that.result) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestNumberA, requestNumberB, result, createdAt);
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "id='" + id + '\'' +
                ", requestNumberA=" + requestNumberA +
                ", requestNumberB=" + requestNumberB +
                ", result=" + result +
                ", createdAt=" + createdAt +
                '}';
    }
}
