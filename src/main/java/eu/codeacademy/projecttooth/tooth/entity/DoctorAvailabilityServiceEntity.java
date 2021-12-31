package eu.codeacademy.projecttooth.tooth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctor_availability_service")
@Getter
@Setter
@NoArgsConstructor
public class DoctorAvailabilityServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long doctorAvailabilityServiceId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private ServiceEntity serviceEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_availability_id")
    private DoctorAvailabilityEntity doctorAvailabilityEntity;

    @OneToMany(mappedBy = "doctorAvailabilityServiceEntity")
    private Set<AppointmentEntity> appointmentEntities;
}
