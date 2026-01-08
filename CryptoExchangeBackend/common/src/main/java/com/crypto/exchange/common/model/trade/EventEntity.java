package com.crypto.exchange.common.model.trade;

import com.crypto.exchange.common.model.support.EntitySupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "events", uniqueConstraints=@UniqueConstraint(name="UNI_PRE_ID", columnNames = {"previousId"}))
public class EventEntity implements EntitySupport {

    @Id
    @Column(nullable = false, updatable = false)
    public Long sequenceId;

    @Column(nullable = false, updatable = false)
    public Long previousId;

    @Column(nullable = false, updatable = false, length = VAR_CHAR_10000)
    public String data;

    @Column(nullable = false, updatable = false)
    public Long createdAt;

    @Override
    public String toString() {
        return "EventEntity [" + "sequenceId=" + sequenceId + ", previousId=" + previousId +
                ", data='" + data + '\'' + ", createdAt=" + createdAt + ']';
    }
}
