package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import org.springframework.stereotype.Service;

@Service
public class DoctorEntityMapper {

    public DoctorEntity getDoctorEntity(Doctor doctor,UserEntity userEntity){
        DoctorEntity entity = new DoctorEntity();
        entity.setDoctorLicense(doctor.getDoctorLicense());
        entity.setLocationId(doctor.getLocationId());
        entity.setQualification(doctor.getQualification());
        entity.setUser(userEntity);
        return entity;
    }
}
