package com.telegram.bot.bik.service.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerInvoker {

    @Scheduled(cron = "0 0 9 * * *")
    public void invoke() {

    }
}
