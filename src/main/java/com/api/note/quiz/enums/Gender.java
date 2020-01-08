package com.api.note.quiz.enums;

/**
 * TODO あとで修正
 */
public enum Gender {

    FEMALE(0, "female"),
    MALE(1, "male"),
    OTHER(2, "neutral");

    private int value;

    private String facebookGender;

    Gender(int value, String facebookGender) {
        this.value = value;
        this.facebookGender = facebookGender;
    }

    public static Gender valueOf(Integer value) {
        Gender[] enums = values();
        for (Gender status : enums) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Illegal value");
    }

    public static Gender facebookGenderOf(String value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        Gender[] enums = values();
        for (Gender status : enums) {
            if (status.facebookGender.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Illegal value");
    }

    public int getValue() {
        return this.value;
    }

}
