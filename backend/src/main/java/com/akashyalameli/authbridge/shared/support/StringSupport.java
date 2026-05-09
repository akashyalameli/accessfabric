package com.akashyalameli.authbridge.shared.support;

public final class StringSupport {

    private StringSupport() {
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNotNullAndNotEmpty(String value) {
        return !isNullOrEmpty(value);
    }
}
