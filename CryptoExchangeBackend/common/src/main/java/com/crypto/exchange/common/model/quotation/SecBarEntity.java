package com.crypto.exchange.common.model.quotation;

import com.crypto.exchange.common.model.support.AbstractBarEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sec_bars")
public class SecBarEntity extends AbstractBarEntity{
    
}
