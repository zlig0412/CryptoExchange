package com.crypto.exchange.common.model.trade;

import java.beans.Transient;
import java.math.BigDecimal;

import com.crypto.exchange.common.enums.Direction;
import com.crypto.exchange.common.enums.OrderStatus;
import com.crypto.exchange.common.model.support.EntitySupport;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity implements EntitySupport, Comparable<OrderEntity> {

    @Id
    @Column(nullable = false, updatable = false)
    public Long id;

    @Column(nullable = false, updatable = false)
    public Long sequenceId;

    @Column(nullable = false, updatable = false, length = VAR_ENUM)
    public Direction direction;

    @Column(nullable = false, updatable = false)
    public Long userId;

    @Column(nullable = false, updatable = false, length = VAR_ENUM)
    public OrderStatus status;

    @Column(nullable = false, updatable = false, precision = PRECISION, scale = SCALE)
    public BigDecimal price;

    @Column(nullable = false, updatable = false)
    public Long createdAt;

    @Column(nullable = false, updatable = false)
    public Long updatedAt;

    @Column(nullable = false, updatable = false, precision = PRECISION, scale = SCALE)
    public BigDecimal quantity;

    @Column(nullable = false, updatable = false, precision = PRECISION, scale = SCALE)
    public BigDecimal unfilledQuantity;

    private int version;

    @Transient
    @JsonIgnore
    public int getVersion() {
        return version;
    }

    public void updateOrder(BigDecimal unfilledQuantity, OrderStatus status, long updatedAt) {
        this.version++;
        this.unfilledQuantity = unfilledQuantity;
        this.status = status;
        this.updatedAt = updatedAt;
        this.version++;
    }

    @Nullable
    public OrderEntity copy() {
        OrderEntity entity = new OrderEntity();
        int ver = this.version;
        entity.status = this.status;
        entity.unfilledQuantity = this.unfilledQuantity;
        entity.updatedAt = this.updatedAt;
        if (ver != this.version) {
            return null;
        }

        entity.createdAt = this.createdAt;
        entity.direction = this.direction;
        entity.id = this.id;
        entity.price = this.price;
        entity.quantity = this.quantity;
        entity.sequenceId = this.sequenceId;
        entity.userId = this.userId;
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OrderEntity e) {
            return this.id.longValue() == e.id.longValue();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "OrderEntity [id=" + id + ", sequenceId=" + sequenceId + ", direction=" + direction + ", userId="
                + userId + ", status=" + status + ", price=" + price + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + ", version=" + version + ", quantity=" + quantity + ", unfilledQuantity="
                + unfilledQuantity + "]";
    }

    /**
     * 按OrderID排序
     */
    @Override
    public int compareTo(OrderEntity o) {
        return Long.compare(this.id, o.id);
    }
    
}
