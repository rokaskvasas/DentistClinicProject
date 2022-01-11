package eu.codeacademy.projecttooth.tooth.mapper;


import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorMapper {

    private final LocationMapper locationMapper;

    public DoctorEntity createDoctorEntity(Doctor doctor, UserEntity userEntity) {

        return DoctorEntity.builder()
                .doctorLicense(doctor.getDoctorLicense())
                .locationId(doctor.getLocation().getLocationID())
                .qualification(doctor.getQualification())
                .status(doctor.getStatus())
                .user(userEntity)
                .build();
    }

    public Doctor createModel(DoctorEntity entity) {
        return Doctor.builder()
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .doctorLicense(entity.getDoctorLicense())
                .qualification(entity.getQualification())
                .location(locationMapper.createModel(entity.getLocation()))
                .status(entity.getStatus())
                .doctorId(entity.getDoctorId())
                .build();
    }

//    public DoctorDto createDtoModel(DoctorEntity entity){
//        return DoctorDto.builder()
//                .firstName(entity.getUser().getFirstName())
//                .lastName(entity.getUser().getLastName())
//                .phoneNumber(entity.getUser().getPhoneNumber())
//                .location(locationMapper.createModel(entity.getLocation()))
//                .doctorId(entity.getDoctorId())
//                .build();
//    }

}
