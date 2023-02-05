package com.kpi.diploma.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {
	private final ErrorDetails errorDetails;

	public ValidationException(String message, ErrorDetails errorDetails) {
		super(message);
		this.errorDetails = errorDetails;
	}
}
