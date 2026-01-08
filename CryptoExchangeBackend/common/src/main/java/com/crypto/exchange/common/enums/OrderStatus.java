package com.crypto.exchange.common.enums;

public enum OrderStatus {
    PENDING(false),
    FULLY_FILLED(true),
    PARTIAL_FILLED(false),
    FULLY_CANCELED(true),
    PARTIAL_CANCELED(true);

    private final boolean isFinalStatus;

    OrderStatus(boolean isFinalStatus){
        this.isFinalStatus = isFinalStatus;
    }

    public boolean isFinalStatus(){
        return isFinalStatus;
    }
}
