package eu.codeacademy.projecttooth.tooth.entity;

import eu.codeacademy.projecttooth.tooth.model.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.ServiceEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long serviceId;

    @Enumerated(EnumType.STRING)
    private ServiceEnum serviceName;

    @Enumerated(EnumType.STRING)
    private QualificationEnum minimumQualification;

    @OneToOne(mappedBy = "serviceEntity")
    private DoctorAvailabilityServiceEntity doctorAvailabilityServiceEntity;

}
