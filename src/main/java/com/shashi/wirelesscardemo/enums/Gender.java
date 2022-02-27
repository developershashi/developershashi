package com.shashi.wirelesscardemo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Gender {

    AGENDER,
    BIGENDER,
    FEMALE,
    GENDERFLUID,
    GENDERQUEER,
    MALE,
    /*NON-BINARY,*/
    POLYGENDER;

    @JsonCreator
    public static Gender setValue(String key) {
        return Arrays.stream(Gender.values())
                .filter(exampleEnum -> exampleEnum.toString().equals(key.toUpperCase()))
                .findAny()
                .orElse(null);
    }

}
