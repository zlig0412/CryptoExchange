package com.crypto.exchange.common.model.support;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBarEntity implements EntitySupport{
    
    @Id
    @Column(nullable = false, updatable = false)
    public Long startTime;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal openPrice;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal closePrice;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal highPrice;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal lowPrice;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal volume;

    @Transient
    @JsonIgnore
    public Number[] getBarData(){
        return new Number[]{startTime, openPrice, closePrice, highPrice, lowPrice, volume};
    }

    public String toString(ZoneId zoneId){
        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(startTime), zoneId);
        String time = FORMATTERS.get(getClass().getSimpleName()).format(zdt);
        return String.format("{%s: startTime=%s, O=%s, H=%s, L=%s, C=%s, qty=%s}", this.getClass().getSimpleName(),
                time, this.openPrice, this.highPrice, this.lowPrice, this.closePrice, this.volume);
    }

    @Override
    public String toString() {
        return toString(ZoneId.systemDefault());
    }

    static final Map<String, DateTimeFormatter> FORMATTERS = Map.of(
        "SecBarEntity", DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US),
        "MinBarEntity", DateTimeFormatter.ofPattern("dd HH:mm", Locale.US),
        "HourBarEntity", DateTimeFormatter.ofPattern("MM-dd HH", Locale.US),
        "DayBarEntity", DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    );
}
