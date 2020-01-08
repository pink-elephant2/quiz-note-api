package com.api.note.quiz.enums;

/**
 * TODO あとで修正
 */
public enum UserType {
    USER(0, "E-mail"),
    FACEBOOK(1, "Facebook"),
    LINE(2, "LINE");

    private int value;
    private String label;

    UserType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static UserType valueOf(Integer value) {
        UserType[] enums = values();
        for (UserType status : enums) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Illegal value");
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        return label;
    }
}
