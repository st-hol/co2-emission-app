package com.kpi.diploma.domain.type;

import com.kpi.diploma.domain.type.typed.StringTypeEnum;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EcoType implements StringTypeEnum {

    GREEN("Green"),
    RED("Red"),
    UNDEFINED("Undefined");

    private static final double ECO_FRIENDLY_POINT = 150;
    private final String value;

    EcoType(String v) {
        this.value = v;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equalsTo(String value) {
        return this.value.equals(value);
    }

    public boolean isEcoFriendly(double co2emissions) {
        return co2emissions < ECO_FRIENDLY_POINT;
    }
}
