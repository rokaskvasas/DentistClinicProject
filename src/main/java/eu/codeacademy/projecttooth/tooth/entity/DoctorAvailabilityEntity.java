package eu.codeacademy.projecttooth.tooth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "doctor_availability")
@Getter
@Setter
@NoArgsConstructor
public class DoctorAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long doctorAvailabilityId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "doctor_id")
    private Long doctorId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false, insertable = false, updatable = false)
    private DoctorEntity doctorEntity;

    @OneToMany(mappedBy = "doctorAvailabilityEntity")
    private Set<DoctorAvailabilityServiceEntity> doctorAvailabilityServiceEntities;
}
