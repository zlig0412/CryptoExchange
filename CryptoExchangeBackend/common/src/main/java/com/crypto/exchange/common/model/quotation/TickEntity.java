package com.crypto.exchange.common.model.quotation;

import java.math.BigDecimal;

import com.crypto.exchange.common.model.support.EntitySupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ticks", uniqueConstraints=@UniqueConstraint(name="UNI_T_M", 
       columnNames = {"takerOrderId", "makerOrderId",}),
       indexes=@Index(name="IDX_CAT", columnList="createdAt"))
public class TickEntity implements EntitySupport{
    
    @Id
    @Column(nullable = false, updatable = false)
    public Long id;

    @Column(nullable = false, updatable = false)
    public Long SequenceId;

    @Column(nullable = false, updatable = false)
    public Long takerOrderId;

    @Column(nullable = false, updatable = false)
    public Long makerOrderId;


    @Column(nullable = false, updatable = false)
    public boolean takerDirection;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal price;

    @Column(nullable = false, updatable = false, precision=PRECISION, scale=SCALE)
    public BigDecimal quantity;


    @Column(nullable = false, updatable = false)
    public Long createdAt;

    public String toJson() {
        return "[" + createdAt + "," + (takerDirection ? 1 : 0) + "," + price + "," + quantity + "]";
    }
}
