package com.kpi.diploma.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kpi.diploma.dto.DriveTripDto;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("squid:S2068")
@Slf4j
@Component
public class NewTripValidator implements Validator {

	private static final String NOT_EMPTY = "NotEmpty";
	private static final String INVALID = "INVALID";
	private static final double ERROR = 0.000000001;

	@Override
	public boolean supports(Class<?> aClass) {
		return DriveTripDto.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		DriveTripDto dto = (DriveTripDto) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "from", NOT_EMPTY,
				"Departure can't be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "to", NOT_EMPTY,
				"Destination can't be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "distanceKm", NOT_EMPTY,
				"Distance cant' be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "saveToHistory", NOT_EMPTY,
				"Please chose whether you want to save trip in trip history.");

		if (!dto.isTestTrip()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", NOT_EMPTY,
					"Trip name can't be empty.");
		}
		if (!dto.isTestTrip() && dto.getCarId() == null) {
			errors.rejectValue("carId", NOT_EMPTY, "No car specified.");
		}
		if (dto.getDistanceKm() < ERROR) {
			errors.rejectValue("distanceKm", NOT_EMPTY, "Distance must be positive integer value.");
		}
		if (dto.getTo().equals(dto.getFrom())) {
			errors.rejectValue("to", INVALID, "Destination can't be equal to departure.");
		}
		log.error("validate result is {}", errors);
	}

}
