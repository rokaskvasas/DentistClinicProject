package eu.codeacademy.projecttooth.tooth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDoctorServiceAvailabilityDto {

    private Long doctorServiceAvailabilityId;

    private Long doctorAvailabilityId;

    private Long serviceId;
}
