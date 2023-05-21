package com.telegram.bot.bik.service.scheduler;

import com.telegram.bot.bik.api.parser.ParserNameSpecialization;
import com.telegram.bot.bik.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleInvokerGroup {

    private final ParserNameSpecialization parserNameSpecialization;
    private final GroupService groupService;

    @Scheduled(cron = "${schedule.cron.group}")
    public void invokeGroup() {
        log.info("[NOTIFCATION] search group start");
        List<String> specializations = parserNameSpecialization.parseSpecialization()
                        .stream().toList();
        groupService.saveAll(specializations);
        log.info("[NOTIFCATION] search group end");

    }
}
