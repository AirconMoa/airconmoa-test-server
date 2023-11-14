package com.airconmoa.airconmoa.constant;

import lombok.Getter;

@Getter
public enum InstallInfo {
    NEW_BUILDING(1),
    EXISTING_BUILDING(2),
    AIRCON_REPLACEMENT(3);

    private int value;

    InstallInfo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
