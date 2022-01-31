package eu.codeacademy.projecttooth.tooth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDoctorServiceAvailabilityDto {

    private Long doctorServiceAvailabilityId;
    @NotNull
    private Long doctorAvailabilityId;
    @NotNull
    private Long serviceId;
}
