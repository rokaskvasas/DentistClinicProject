package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    private Long serviceId;

    private ServiceEnum service;

    private QualificationEnum minimumQualification;
}
