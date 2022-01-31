package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DoctorServiceAvailabilitySearchCriteria {

    private ServiceEnum service;
    private String city, firstName, lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime, endTime;

}
