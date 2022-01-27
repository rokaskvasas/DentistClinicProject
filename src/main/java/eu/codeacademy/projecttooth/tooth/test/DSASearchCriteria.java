package eu.codeacademy.projecttooth.tooth.test;

import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DSASearchCriteria {

    private ServiceEnum service;
    private String city, firstName, lastName;
    private LocalDateTime startTime, endTime;

}
