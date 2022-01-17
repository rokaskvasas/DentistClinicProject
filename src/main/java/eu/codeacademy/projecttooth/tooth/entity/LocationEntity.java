package eu.codeacademy.projecttooth.tooth.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

}
