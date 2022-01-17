package eu.codeacademy.projecttooth.tooth.scheduler;

import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAvailabilityScheduler {

    private final DoctorAvailabilityService service;

    // cron setting = top of every hour, on the hour 8-to-21 weekdays
    @Scheduled(cron = "0 0 8-21 * * MON-FRI")
    public void deleteExpiredAvailability(){
        service.deleteExpiredAvailability();
    }
}
