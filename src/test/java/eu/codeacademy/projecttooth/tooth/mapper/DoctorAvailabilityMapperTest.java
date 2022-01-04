package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class DoctorAvailabilityMapperTest {

    @Test
    public void checkDoctorAvailabilityTimeIsCorrect(){
        DoctorAvailability doctorAvailability = new DoctorAvailability();
        doctorAvailability.setStartTime(LocalDateTime.of(2021, Month.DECEMBER,3,12, 0,0));
        doctorAvailability.setEndTime(LocalDateTime.now());
        assertTrue(doctorAvailability.getStartTime().isBefore(doctorAvailability.getEndTime()));

    }
    @Test
    public void checkDoctorAvailabilityIsSameDay(){
        DoctorAvailability doctorAvailability = new DoctorAvailability();
        doctorAvailability.setStartTime(LocalDateTime.of(2021, Month.DECEMBER,3,12, 0,0));
        doctorAvailability.setEndTime(LocalDateTime.of(2021, Month.DECEMBER,3,13, 0,0));

        assertEquals(doctorAvailability.getStartTime().toLocalDate(), doctorAvailability.getEndTime().toLocalDate());
    }

}