package com.kpi.diploma.payload;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorDetails implements Serializable {
	private final String message;
	private final HttpStatus status;
	private final Map<String, String> details;

}
