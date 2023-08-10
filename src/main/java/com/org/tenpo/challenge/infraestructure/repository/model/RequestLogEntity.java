package com.org.tenpo.challenge.infraestructure.repository.model;

import com.org.tenpo.challenge.core.model.RequestLog;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("request_log")
public class RequestLogEntity {

    @Id
    private String id;

    @Column("request_number_a")
    private Double requestNumberA;

    @Column("request_number_b")
    private Double requestNumberB;

    private Double result;

    @Column("created_at")
    private Date createdAt;

    public RequestLogEntity() {
    }

    public RequestLogEntity(RequestLog requestLog) {
        BeanUtils.copyProperties(requestLog, this);
    }

    public RequestLogEntity(String id, Double requestNumberA, Double requestNumberB, Double result, Date createdAt) {
        this.id = id;
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
        this.result = result;
        this.createdAt = createdAt;
    }

    public RequestLog toModel() {
        var model = new RequestLog();
        BeanUtils.copyProperties(this, model);
        return model;
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
}
