package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorServiceAvailability {

    private Long doctorAvailabilityServiceId;

    private Long doctorAvailabilityId;

    private Long serviceId;



}