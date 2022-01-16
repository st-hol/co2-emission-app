package com.kpi.diploma.domain.type.typed;

import com.kpi.diploma.domain.exception.UnknownEnumValueException;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


public interface TypeEnum<T> {

    static <E extends Enum<E> & TypeEnum<T>, T> E findEnumValueOrThrowException(Class<E> enumClass, T value) {
        Optional<E> optionalEnumValue = findOptionalEnumValue(enumClass, value);
        return optionalEnumValue
                .orElseThrow(() -> new UnknownEnumValueException(
                        enumClass.getSimpleName(),
                        value != null ? value.toString() : "null value"));

    }

    static <E extends Enum<E> & TypeEnum<T>, T> Optional<E> findOptionalEnumValue(Class<E> enumClass, T value) {
        return Stream.of(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getValue(), value))
                .findFirst();
    }

    /**
     * @return T code value for enum
     */
    T getValue();

    /**
     * @param value code to compare
     * @return true, if enum code equals to passed value
     */
    default boolean equalsTo(T value) {
        return getValue().equals(value);
    }
}
