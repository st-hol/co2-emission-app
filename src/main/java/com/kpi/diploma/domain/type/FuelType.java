package com.kpi.diploma.domain.type;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kpi.diploma.domain.type.typed.StringTypeEnum;

public enum FuelType implements StringTypeEnum {

	PETROL("Petrol"),
	DIESEL("Diesel"),
	GAS("Gas"),
	HYBRID("Hybrid"),
	UNDEFINED("Undefined");

	private final String value;

	FuelType(String v) {
		this.value = v;
	}

	public static List<FuelType> getValidTypes() {
		return Stream.of(values()).filter(v -> !v.equals(UNDEFINED)).collect(Collectors.toList());
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
