package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    private List<ModifyDoctorServiceAvailabilityDto> doctorServiceAvailabilityList;

}
