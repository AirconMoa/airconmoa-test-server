package com.airconmoa.airconmoa.constant;

import lombok.Getter;

@Getter
public enum BuildingType {
    APARTMENT(1),
    HOUSING(2),
    VILLA(3),
    BUILDING(4),
    OTHERS(5);

    private int value;

    BuildingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
