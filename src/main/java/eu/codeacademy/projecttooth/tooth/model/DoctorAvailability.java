package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailability {

    private Long doctorAvailabilityId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Doctor doctor;

}
