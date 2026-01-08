package com.crypto.exchange.common.model.trade;

import java.math.BigDecimal;

import com.crypto.exchange.common.enums.AssetType;
import com.crypto.exchange.common.model.support.EntitySupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transfer_logs")
public class TransferLogEntity implements EntitySupport{
    
    @Id
    @Column(nullable = false, updatable = false, length=VAR_ENUM)
    public String trasferId;

    @Column(nullable = false, updatable = false, length=VAR_ENUM)
    public AssetType assetType;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal amount;

    @Column(nullable = false, updatable = false)
    public Long userId;

    @Column(nullable = false, updatable = false)
    public Long createdAt;

    @Column(nullable = false, length=VAR_ENUM)
    public String type;

    @Column(nullable = false, length=VAR_ENUM)
    public String status;
}
