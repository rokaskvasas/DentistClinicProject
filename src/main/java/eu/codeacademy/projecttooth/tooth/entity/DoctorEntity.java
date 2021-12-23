package eu.codeacademy.projecttooth.tooth.entity;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long doctorId;

   @Column(name = "user_id", insertable = false, updatable = false)
   private Long userId;

    @Column(name = "doctor_license")
    private String doctorLicense;

    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private QualificationEnum qualification;

    @Column(name = "location_id", insertable = false, updatable = false)
    private Long locationId;

    @OneToOne @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userDoctorEntity;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity locationEntity;

    @OneToMany(mappedBy = "doctorEntity")
    private Set<DoctorAvailabilityEntity> doctorAvailabilityEntities;

}
