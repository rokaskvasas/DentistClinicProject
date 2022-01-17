package eu.codeacademy.projecttooth.tooth.mapper;


import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorMapper {

    private final LocationMapper locationMapper;

    public DoctorEntity createDoctorEntity(Doctor doctor, UserEntity userEntity, LocationEntity locationEntity) {

        return DoctorEntity.builder()
                .doctorLicense(doctor.getDoctorLicense())
                .locationId(doctor.getLocation().getLocationId())
                .location(locationEntity)
                .qualification(doctor.getQualification())
                .status(doctor.getStatus())
                .user(userEntity)
                .build();
    }

    public Doctor createModel(DoctorEntity entity) {
        Doctor.DoctorBuilder<?, ?> builder = Doctor.builder();
        setUserDetailsToDoctor(builder, entity.getUser());
        return builder
                .doctorLicense(entity.getDoctorLicense())
                .qualification(entity.getQualification())
                .location(locationMapper.createModel(entity.getLocation()))
                .status(entity.getStatus())
                .doctorId(entity.getDoctorId())
                .build();
    }

    private void setUserDetailsToDoctor(Doctor.DoctorBuilder<?, ?> builder, UserEntity user) {
        if (Objects.nonNull(user)) {
            builder
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole());
        }
    }

}
