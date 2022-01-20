package eu.codeacademy.projecttooth.tooth.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "doctor_availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long doctorAvailabilityId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

//    @Column(name = "doctor_id", nullable = false)
//    private Long doctorId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorEntity;

    @OneToMany(mappedBy = "doctorAvailability", cascade = CascadeType.ALL)
    private Set<DoctorServiceAvailabilityEntity> doctorAvailabilityServiceEntities;
}
