package com.crypto.exchange.common.enums;

public enum Direction {
    BUY(1),
    SELL(0);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public static Direction of(int value){
        return value == 1 ? BUY : SELL;
    }

    public Direction negate(){
        return this == BUY ? SELL : BUY;
    }
}
