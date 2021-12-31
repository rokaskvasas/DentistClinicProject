package eu.codeacademy.projecttooth.tooth.entity;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long doctorId;


    @Column(name = "doctor_license")
    private String doctorLicense;

    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private QualificationEnum qualification;

    @Column(name = "location_id")
    private Long locationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "location_id",insertable = false, updatable = false)
    private LocationEntity locationEntity;

    @OneToMany(mappedBy = "doctorEntity")
    private Set<DoctorAvailabilityEntity> doctorAvailabilityEntities;

}
