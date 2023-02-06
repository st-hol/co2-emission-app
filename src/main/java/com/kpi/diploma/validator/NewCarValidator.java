package com.kpi.diploma.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kpi.diploma.dto.GarageCarDto;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("squid:S2068")
@Slf4j
@Component
public class NewCarValidator implements Validator {

    //messages
    private static final String NOT_EMPTY = "NotEmpty";

    @Override
    public boolean supports(Class<?> aClass) {
        return GarageCarDto.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        GarageCarDto dto = (GarageCarDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearManufactured", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "engineSize", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cylinders", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fuelConsumptionComb", NOT_EMPTY);
        log.error("{}", errors);
    }

}
