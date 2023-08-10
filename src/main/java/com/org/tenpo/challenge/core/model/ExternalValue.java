package com.org.tenpo.challenge.core.model;

import java.util.Date;

public class ExternalValue {

    private Double percentage;

    private Date createdAt;

    public ExternalValue() {
    }

    public ExternalValue(Double percentage) {
        this.percentage = percentage;
        this.createdAt = new Date();
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
