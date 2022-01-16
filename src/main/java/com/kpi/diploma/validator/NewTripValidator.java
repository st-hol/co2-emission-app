package com.kpi.diploma.validator;

import com.kpi.diploma.dto.CreateCarDto;
import com.kpi.diploma.dto.CreateTripDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@SuppressWarnings("squid:S2068")
@Slf4j
@Component
public class NewTripValidator implements Validator {

    //messages
    private static final String NOT_EMPTY = "NotEmpty";

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateTripDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateTripDto dto = (CreateTripDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "from", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "to", NOT_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "distanceKm", NOT_EMPTY);
        log.error("{}", errors);
    }

}
