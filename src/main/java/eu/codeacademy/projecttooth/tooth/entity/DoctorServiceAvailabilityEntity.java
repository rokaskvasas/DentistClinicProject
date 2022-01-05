package eu.codeacademy.projecttooth.tooth.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctor_availability_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorServiceAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long doctorAvailabilityServiceId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "service_id")
    private ServiceEntity serviceEntity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doctor_availability_id")
    private DoctorAvailabilityEntity doctorAvailabilityEntity;

    @OneToMany(mappedBy = "doctorAvailabilityService")
    private Set<AppointmentEntity> appointmentEntities;
}
