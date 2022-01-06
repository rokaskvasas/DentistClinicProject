package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AppointmentDto extends DoctorDto {

    private Long appointmentId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ServiceEnum serviceEnum;

}
