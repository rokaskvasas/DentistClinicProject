package eu.codeacademy.projecttooth.tooth.mapper;


import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorResponseDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorMapper {

    private final LocationMapper locationMapper;
    private final UserMapper userMapper;

    public DoctorEntity createDoctorEntity(Doctor doctor) {

        return DoctorEntity.builder()
                .doctorLicense(doctor.getDoctorLicense())
                .locationId(doctor.getLocationId())
                .qualification(doctor.getQualification())
                .status(doctor.getStatus())
                .user(userMapper.getUserEntity(doctor))
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

    public DoctorDto createDtoModel(DoctorEntity entity) {
        DoctorDto.DoctorDtoBuilder builder = DoctorDto.builder();
        setUserDetailsToDoctorDto(builder, entity.getUser());
        return builder
                .doctorId(entity.getDoctorId())
                .doctorLicense(entity.getDoctorLicense())
                .qualification(entity.getQualification())
                .location(locationMapper.createModel(entity.getLocation()))
                .build();
    }

    private void setUserDetailsToDoctorDto(DoctorDto.DoctorDtoBuilder builder, UserEntity user) {
        if (Objects.nonNull(user)) {
            builder.firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber());
        }
    }

    public DoctorResponseDto createRegisterDtoModel(DoctorEntity entity) {
        return DoctorResponseDto.builder().doctorId(entity.getDoctorId())
                .doctorLicense(entity.getDoctorLicense())
                .email(entity.getUser().getEmail())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .location(locationMapper.createModel(entity.getLocation()))
                .phoneNumber(entity.getUser().getPhoneNumber())
                .qualification(entity.getQualification())
                .status(entity.getStatus())
                .userId(entity.getUser().getUserId())
                .build();
    }

}
