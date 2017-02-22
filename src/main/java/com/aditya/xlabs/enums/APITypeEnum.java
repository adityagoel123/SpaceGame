package com.aditya.xlabs.enums;

public enum APITypeEnum {
    USER_API("USER_API"), PROTOCOL_API("PROTOCOL_API");

    private String code;

    APITypeEnum(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static APITypeEnum fromString(String string) {
        if (string != null) {
            for (APITypeEnum b : APITypeEnum.values()) {
                if (string.equalsIgnoreCase(b.code())) {
                    return b;
                }
            }
        }
        return null;
    }

}
