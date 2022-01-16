package com.kpi.diploma.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Common config for feign clients.
 */
@Configuration
public class FeignClientConfiguration {

    /**
     * There are four logging levels to choose from:
     * NONE – no logging, which is the default
     * BASIC – log only the request method, URL, and response status
     * HEADERS – log the basic information together with request and response headers
     * FULL – log the body, headers, and metadata for both request and response
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


}