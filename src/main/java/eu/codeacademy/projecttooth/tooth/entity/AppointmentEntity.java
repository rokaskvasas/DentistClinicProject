package eu.codeacademy.projecttooth.tooth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long appointmentId;

    @Column(name = "patient_id", insertable = false, updatable = false)
    private Long patientId;

    @Column(name = "doctor_availability_service_id")
    private Long doctorAvailabilityServiceId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "doctor_availability_service_id", insertable = false, updatable = false)
    private DoctorAvailabilityServiceEntity doctorAvailabilityServiceEntity;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientEntity patientEntity;


}
