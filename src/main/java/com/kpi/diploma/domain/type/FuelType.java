package com.kpi.diploma.domain.type;

import com.kpi.diploma.domain.type.typed.StringTypeEnum;

public enum FuelType implements StringTypeEnum {

    GAS("Gas"),
    DIESEL("Diesel"),
    UNDEFINED("Undefined");

    private final String value;

    FuelType(String v) {
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
}
