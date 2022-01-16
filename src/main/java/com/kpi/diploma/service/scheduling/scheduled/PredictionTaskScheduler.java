package com.kpi.diploma.service.scheduling.scheduled;

import com.kpi.diploma.service.scheduling.SchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class PredictionTaskScheduler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SchedulingService schedulingService;

    /**
     * every new year
     */
    @Scheduled(cron = "@yearly")
    public void scheduledPrediction() {
        log.info("Scheduled job Started.\n" +
                "The time now is {}", formatter.format(LocalDateTime.now()));
        schedulingService.process();
        log.info("Scheduled job Finished.");
    }
}