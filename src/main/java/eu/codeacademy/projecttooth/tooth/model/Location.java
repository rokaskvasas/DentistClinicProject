package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Long locationID;

    private String locationName;

    private String city;

}
