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

    @Column(name = "doctor_availability_id", insertable = false, updatable = false)
    private Long doctorAvailabilityId;

    @Column(name = "service_id", insertable = false, updatable = false)
    private Long serviceId;

    @OneToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private ServiceEntity serviceEntity;

    @ManyToOne
    @JoinColumn(name = "doctor_availability_id", referencedColumnName = "id")
    private DoctorAvailabilityEntity doctorAvailabilityEntity;

    @OneToMany(mappedBy = "doctorAvailabilityServiceEntity")
    private Set<AppointmentEntity> appointmentEntities;
}
