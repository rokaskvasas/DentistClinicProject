package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import org.springframework.stereotype.Service;

@Service
public class DoctorEntityMapper {

    public DoctorEntity getDoctorEntity(Doctor doctor,UserEntity userEntity){

        return DoctorEntity.builder()
                .doctorLicense(doctor.getDoctorLicense())
                .locationId(doctor.getLocationId())
                .qualification(doctor.getQualification())
                .user(userEntity)
                .build();
    }
    public Doctor getDoctorModel(DoctorEntity entity){
        return Doctor.builder()
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .doctorLicense(entity.getDoctorLicense())
                .qualification(entity.getQualification())
                .locationName(entity.getLocationEntity().getName())
                .locationCity(entity.getLocationEntity().getCity())
                .status(entity.getStatus())
                .build();
    }
}
