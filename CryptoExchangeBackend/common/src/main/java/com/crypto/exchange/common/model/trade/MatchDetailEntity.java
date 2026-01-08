package com.crypto.exchange.common.model.trade;

import java.math.BigDecimal;

import com.crypto.exchange.common.enums.Direction;
import com.crypto.exchange.common.enums.MatchType;
import com.crypto.exchange.common.model.support.EntitySupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "match_details", 
       uniqueConstraints=@UniqueConstraint(name="UNI_OID_COID", 
       columnNames = {"orderId", "counterOrderId"}),
       indexes=@Index(name="IDX_OID_CT", columnList="orderId, createdAt")
      )
public class MatchDetailEntity implements EntitySupport, Comparable<MatchDetailEntity>{

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
    public MatchType type;

    @Column(nullable=false, updatable=false, length=VAR_ENUM)
    public Direction direction;

    @Column(nullable=false, updatable=false, precision=PRECISION, scale=SCALE)
    public BigDecimal price;

    @Column(nullable=false, updatable=false, precision=PRECISION, scale=SCALE)
    public BigDecimal quantity;

    @Column(nullable=false)
    public Long createdAt;

    @Override
    public int compareTo(MatchDetailEntity o) {
        int cmp = Long.compare(this.orderId, o.orderId);
        if(cmp == 0){
            cmp = Long.compare(this.counterOrderId, o.counterOrderId);
        }
        return cmp;
    }
    
}
