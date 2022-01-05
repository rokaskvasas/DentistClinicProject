package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorMapper {


    public DoctorEntity createDoctorEntity(Doctor doctor, UserEntity userEntity) {

        return DoctorEntity.builder()
                .doctorLicense(doctor.getDoctorLicense())
                .locationId(doctor.getLocationId())
                .qualification(doctor.getQualification())
                .status(doctor.getStatus())
                .user(userEntity)
                .build();
    }

    public Doctor createDoctorModel(DoctorEntity entity) {
        return Doctor.builder()
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .doctorLicense(entity.getDoctorLicense())
                .qualification(entity.getQualification())
                .locationName(entity.getLocation().getName())
                .locationCity(entity.getLocation().getCity())
                .status(entity.getStatus())
                .doctorId(entity.getDoctorId())
                .build();
    }


}
