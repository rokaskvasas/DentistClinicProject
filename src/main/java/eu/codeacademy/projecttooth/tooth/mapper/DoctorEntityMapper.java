package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.stereotype.Service;

@Service
public class DoctorEntityMapper {

    public DoctorEntity getDoctorEntity(Doctor doctor){
        DoctorEntity entity = new DoctorEntity();
        entity.setDoctorLicense(doctor.getDoctorLicense());
        entity.setLocationId(doctor.getLocationId());
        entity.setQualification(doctor.getQualification());
        return entity;
    }
}
