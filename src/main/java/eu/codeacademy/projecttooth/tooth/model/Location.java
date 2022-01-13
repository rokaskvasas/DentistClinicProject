package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Long locationId;

    private String name;

    private String city;

}
