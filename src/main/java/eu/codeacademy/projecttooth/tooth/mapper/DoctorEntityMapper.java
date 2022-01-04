package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorEntityMapper {


    public DoctorEntity createDoctorEntity(Doctor doctor, UserEntity userEntity){

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
                .locationName(entity.getLocation().getName())
                .locationCity(entity.getLocation().getCity())
                .status(entity.getStatus())
                .build();
    }
    public DoctorEntity updateDoctorEntity(Doctor doctor, DoctorEntity entity){
        return DoctorEntity.builder()
                .doctorLicense(doctor.getDoctorLicense())
                .doctorAvailabilityEntities(entity.getDoctorAvailabilityEntities())
                .doctorId(entity.getDoctorId())
                .locationId(entity.getLocationId())
                .status(entity.getStatus())
                .qualification(doctor.getQualification())
                .user(UserEntity.builder()
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .role(entity.getUser().getRole())
                        .password(entity.getUser().getPassword())
                        .email(entity.getUser().getEmail())
                        .phoneNumber(doctor.getPhoneNumber())
                        .userId(entity.getUser().getUserId())
                        .build())
                .build();
    }

}
