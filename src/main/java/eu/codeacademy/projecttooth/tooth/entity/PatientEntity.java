package eu.codeacademy.projecttooth.tooth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long patientId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @OneToOne @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userPatientEntity;

    @OneToOne(mappedBy = "patientEntity")
    private AppointmentEntity appointmentEntity;
}
