package com.aditya.xlabs.enums;

public enum SpaceShipTypeEnum {
    WINGER("WINGER"), ANGLE("ANGLE"), ACLASS("A-CLASS"), BCLASS("B-CLASS"), SCLASS("S_CLASS");

    private String code;

    SpaceShipTypeEnum(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static SpaceShipTypeEnum fromString(String string) {
        if (string != null) {
            for (SpaceShipTypeEnum b : SpaceShipTypeEnum.values()) {
                if (string.equalsIgnoreCase(b.code())) {
                    return b;
                }
            }
        }
        return null;
    }

}
