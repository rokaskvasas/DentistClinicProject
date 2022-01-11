package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private Long appointmentId;

    private Patient patient;

    private DoctorServiceAvailability serviceAvailability;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
