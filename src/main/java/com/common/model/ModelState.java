package com.common.model;

import lombok.Getter;

public enum ModelState {
    CREATE(1),
    UPDATE(2),
    DELETE(3);
    
    @Getter
    private final int value;

    ModelState(int value) {
        this.value = value;
    }
}
