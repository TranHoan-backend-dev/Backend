package com.base.utils;

public enum FileFormat {
    EXCEL("xlsx"),
    PDF("pdf");

    private final String format;

    FileFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
