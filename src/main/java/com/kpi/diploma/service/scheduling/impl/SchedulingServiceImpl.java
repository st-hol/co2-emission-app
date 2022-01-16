package com.kpi.diploma.service.scheduling.impl;

import com.kpi.diploma.concurrent.BatchExecutionService;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.service.scheduling.SchedulingService;
import com.kpi.diploma.service.base.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Value("${scheduler.predict.num.threads:1}")
    private int numThreads;

    @Autowired
    private UserService userService;

    @Override
    public void process() {
        List<? extends Runnable> tasks = buildUpcomingEventsProcessingTasks();
        BatchExecutionService.execute(tasks, getNumCompilationThreads(), "scheduler", null);
    }

    private List<? extends Runnable> buildUpcomingEventsProcessingTasks() {
        final List<Runnable> tasks = new LinkedList<>();
        Runnable currentTask;
        List<User> users = userService.findAll();
        for (User user : users) {
            currentTask = new RefreshYearlyAmount(user.getId());
            tasks.add(currentTask);
        }

        tasks.sort(Comparator.comparing(
                task -> ((RefreshYearlyAmount) task).getUserId()));

        return tasks;
    }

    private int getNumCompilationThreads() {
        if (numThreads < 1) {
            return Runtime.getRuntime().availableProcessors() + 1;
        } else {
            return numThreads;
        }
    }

    @Getter
    private class RefreshYearlyAmount implements Runnable {

        long userId;

        public RefreshYearlyAmount(long userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            //set 0
            userService.findAll().forEach(u -> {
                u.setCo2YearAmount(BigDecimal.ZERO);
                userService.save(u);
            });
        }
    }

}
