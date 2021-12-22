package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private Long appointmentId;

    private Long patientId;

    private Long DoctorAvailabilityServiceId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
