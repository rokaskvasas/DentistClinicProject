package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorServiceAvailabilityDto {

    private String firstName;

    private String lastName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ServiceEnum serviceEnum;

    private String locationCity;

    private String locationName;

    private Long doctorServiceAvailabilityId;
}
