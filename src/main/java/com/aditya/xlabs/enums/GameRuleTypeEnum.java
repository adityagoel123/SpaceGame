package com.aditya.xlabs.enums;

public enum GameRuleTypeEnum {
    STANDARD("standard"), XSHOT("X-shot"), SUPERCHARGE("super-charge"), DESPERATION("desperation"); 

    private String code;

    GameRuleTypeEnum(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static GameRuleTypeEnum fromString(String string) {
        if (string != null) {
            for (GameRuleTypeEnum b : GameRuleTypeEnum.values()) {
                if (string.equalsIgnoreCase(b.code())) {
                    return b;
                }
            }
        }
        return null;
    }

}
