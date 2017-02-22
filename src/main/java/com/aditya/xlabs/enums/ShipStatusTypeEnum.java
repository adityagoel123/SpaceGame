package com.aditya.xlabs.enums;

public enum ShipStatusTypeEnum {
    HIT("HIT"), MISS("MISS"), KILL("KILL"), NOT_FOUND("NOT_FOUND");

    private String code;

    ShipStatusTypeEnum(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static ShipStatusTypeEnum fromString(String string) {
        if (string != null) {
            for (ShipStatusTypeEnum b : ShipStatusTypeEnum.values()) {
                if (string.equalsIgnoreCase(b.code())) {
                    return b;
                }
            }
        }
        return null;
    }

}
