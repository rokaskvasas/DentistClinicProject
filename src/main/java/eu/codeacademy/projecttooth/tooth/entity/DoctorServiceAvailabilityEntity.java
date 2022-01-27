package eu.codeacademy.projecttooth.tooth.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "reserved")
    private boolean reserved;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doctor_availability_id")
    private DoctorAvailabilityEntity doctorAvailability;

//    @OneToMany(mappedBy = "doctorServiceAvailability")
//    private Set<AppointmentEntity> appointments;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @PrePersist
    public void setCreatedAtDateTime() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();
    }
}
