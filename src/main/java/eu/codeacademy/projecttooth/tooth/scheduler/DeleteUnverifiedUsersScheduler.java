package eu.codeacademy.projecttooth.tooth.scheduler;

import eu.codeacademy.projecttooth.tooth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUnverifiedUsersScheduler {

    private final UserService userService;

    @Scheduled(cron = "@hourly")
    public void deleteUnverifiedUsers(){
        userService.deleteUnverifiedUsers();

    }
}
