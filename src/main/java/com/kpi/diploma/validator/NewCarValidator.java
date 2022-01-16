package com.kpi.diploma.validator;

import com.kpi.diploma.dto.CreateCarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@SuppressWarnings("squid:S2068")
@Slf4j
@Component
public class NewCarValidator implements Validator {

    //messages
    private static final String NOT_EMPTY = "NotEmpty";

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateCarDto.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        CreateCarDto dto = (CreateCarDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearManufactured", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "engineSize", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cylinders", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fuelConsumptionComb", NOT_EMPTY);
        log.error("{}", errors);
    }

}
