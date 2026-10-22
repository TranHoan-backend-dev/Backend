package com.common.utilities;

public class StringUtilities {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrWhiteSpace(String string) {
        return string == null || string.equals(" ");
    }

    public static String sanitizeSheetName(String title) {
        if (title == null) return "Sheet";
        return title.replaceAll("[:/?*\\[\\]]", "_");
    }
}
