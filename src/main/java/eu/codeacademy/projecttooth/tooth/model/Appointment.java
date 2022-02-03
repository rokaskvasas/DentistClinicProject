package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityResponse;
import eu.codeacademy.projecttooth.tooth.dto.PatientResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private Long appointmentId;

    private PatientResponseDto patient;

    private DoctorServiceAvailabilityResponse serviceAvailability;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
