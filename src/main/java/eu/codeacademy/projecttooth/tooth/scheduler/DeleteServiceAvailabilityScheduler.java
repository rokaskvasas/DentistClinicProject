package eu.codeacademy.projecttooth.tooth.scheduler;

import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteServiceAvailabilityScheduler {

    private final DoctorServiceAvailabilityService service;

    // cron setting = top of every hour, on the hour 8-to-21 weekdays
    @Scheduled(cron = "0 0 8-21 * * MON-FRI")
    public void deleteDoctorServiceAvailabilityPastTime(){
        service.deleteExpiredServiceAvailability();
    }
}
