package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduler {

    private ServiceEnum service;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
