package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.*;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorServiceAvailability {

    private Long doctorServiceAvailabilityId;

    private DoctorAvailability doctorAvailability;

    private Service service;



}
