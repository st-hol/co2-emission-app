package com.kpi.diploma.feign.fallback;

import com.kpi.diploma.feign.CO2EmissionsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CO2EmissionsClientFallback implements CO2EmissionsClient {
    @Override
    public double co2Emissions(PredictCO2Request request) {
        log.warn("FALLBACK!");
        return 0;
    }
}
