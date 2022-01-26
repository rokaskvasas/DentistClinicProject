package eu.codeacademy.projecttooth.tooth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDoctorAvailabilityDto {

    private Long doctorAvailabilityId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
