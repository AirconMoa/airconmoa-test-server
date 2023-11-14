package com.airconmoa.airconmoa.domain;

import lombok.Getter;

@Getter
public enum Role {
    USER(1),
    COMPANY(2),
    ADMIN(3);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}