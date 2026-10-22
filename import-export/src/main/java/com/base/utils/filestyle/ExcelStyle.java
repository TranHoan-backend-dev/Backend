package com.base.utils.filestyle;

import lombok.Getter;

public enum ExcelStyle {
    FONT_FAMILY("Times New Roman"),

    FONT_SIZE_TITLE(16),
    FONT_SIZE_SUB_TITLE(14),
    FONT_SIZE_NORMAL(12);

    @Getter
    private final Object value;

    ExcelStyle(Object value) {
        this.value = value;
    }
}
