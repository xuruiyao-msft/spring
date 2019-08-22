package com.springinaction.spittr;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

@Data
public class Spittle {
    private Long id;
    private String message;
    private Date time;
    private Double latitude;
    private Double longtitude;

    public Spittle(Long id, String message) {
        this(id, null, message, null, null);
    }

    public Spittle(Long id, Date time, String message, Double longtitude, Double latitude) {
        this.id = id;
        this.time = time;
        this.message = message;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "id", "message");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "message");
    }
}
