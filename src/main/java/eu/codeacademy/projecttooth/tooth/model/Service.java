package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
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
