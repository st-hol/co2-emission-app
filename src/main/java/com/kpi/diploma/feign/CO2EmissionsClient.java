package com.kpi.diploma.feign;

import com.kpi.diploma.configuration.FeignClientConfiguration;
import com.kpi.diploma.feign.fallback.CO2EmissionsClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "CO2EmissionsClient", url = "http://127.0.0.1:5000/",
        configuration = FeignClientConfiguration.class,  fallback = CO2EmissionsClientFallback.class)
public interface CO2EmissionsClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/predict")
    double co2Emissions(@RequestBody PredictCO2Request request);

    class PredictCO2Request {
        private double enginesize;
        private int cylinders;
        private double fuel;
    }
}