package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorServiceAvailabilityResponse {
    private Long doctorServiceAvailabilityId;
    private DoctorAvailabilityDto doctorAvailabilityDto;
    private Service service;

}
