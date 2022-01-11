package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Long locationID;

    private String name;

    private String city;

}
