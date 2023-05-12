package com.chat.messenger.jobs;

import com.chat.messenger.services.EventService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScreenCaptureEventPublishJob implements Job {

    private final EventService eventService;

    public ScreenCaptureEventPublishJob(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Executing screen capture event publish task");
        eventService.publishScreenCaptureEvent();
    }
}