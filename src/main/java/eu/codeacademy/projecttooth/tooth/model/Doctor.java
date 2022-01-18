package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {

    private Long doctorId;

    private Long userId;
    @NotBlank
    @Size(min = 5, max = 70)
    private String doctorLicense;

    private StatusEnum status;

    private QualificationEnum qualification;

    private Location location;


}
