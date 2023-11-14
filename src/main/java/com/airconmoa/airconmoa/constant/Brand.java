package com.airconmoa.airconmoa.constant;

import lombok.Getter;

public enum Brand {
    LG(1),
    SAMSUNG(2),
    CARRIER(3),
    OTHERS(4);

    private int value;

    Brand(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
