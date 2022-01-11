package eu.codeacademy.projecttooth.tooth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyAppointmentDto {

    private Long doctorServiceAvailabilityId;

    private Long patientId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long appointmentId;

    private Long doctorId;

    private Long serviceId;

}
