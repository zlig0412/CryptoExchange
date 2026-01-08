package com.crypto.exchange.common.model.trade;

import java.math.BigDecimal;

import com.crypto.exchange.common.enums.ClearingType;
import com.crypto.exchange.common.enums.Direction;
import com.crypto.exchange.common.enums.OrderStatus;
import com.crypto.exchange.common.model.support.EntitySupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clearings", uniqueConstraints=@UniqueConstraint(name="UNI_SEQ_ORD_CORD", columnNames = {"sequenceId", "orderId", "counterOrderId"}))
public class ClearingEntity implements EntitySupport{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false, updatable=false)
    public Long id;

    @Column(nullable=false, updatable=false)
    public Long sequenceId;

    @Column(nullable=false, updatable=false)
    public Long orderId;

    @Column(nullable=false, updatable=false)
    public Long counterOrderId;

    @Column(nullable=false, updatable=false)
    public Long userId;

    @Column(nullable=false, updatable=false)
    public Long counterUserId;

    @Column(nullable=false, updatable=false, length=VAR_ENUM)
    public Direction direction;

    @Column(nullable=false, updatable=false)
    public ClearingType type;

    @Column(nullable=false, updatable=false, precision=PRECISION, scale=SCALE)
    public BigDecimal matchPrice;

    @Column(nullable=false, updatable=false, precision=PRECISION, scale=SCALE)
    public BigDecimal matchQuantity;

    @Column(nullable=false, updatable=false, length=VAR_ENUM)
    public OrderStatus orderStatusAfterClearing;

    @Column(nullable=false, updatable=false, precision=PRECISION, scale=SCALE)
    public BigDecimal orderUnfilledQuantityAfterClearing;

    @Column(nullable=false, updatable=false)
    public Long createAt;

    @Override
    public String toString() {
        return "ClearingEntity [id=" + id + ", sequenceId=" + sequenceId + ", orderId=" + orderId + ", counterOrderId=" 
                + counterOrderId + ", userId=" + userId + ", counterUserId=" + counterUserId + ", direction="
                + direction + ", type=" + type + ", matchPrice=" + matchPrice + ", matchQuantity=" + matchQuantity 
                + ", orderStatusAfterClearing=" + orderStatusAfterClearing + ", orderUnfilledQuantityAfterClearing=" 
                + orderUnfilledQuantityAfterClearing + ", createAt=" + createAt + "]";
    }

}
