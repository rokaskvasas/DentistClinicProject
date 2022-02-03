package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.dto.PatientResponseDto;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientMapper {

    private final UserMapper userMapper;

    public PatientEntity createEntity(Patient patient) {

        return PatientEntity.builder()
                .user(userMapper.getUserEntity(patient)).build();
    }

    public Patient createModel(PatientEntity entity) {
        Patient.PatientBuilder<?, ?> builder = Patient.builder();
        setUserDetailsToPatient(entity.getUser(), builder);
        return builder.patientId(entity.getPatientId()).build();
    }

    private void setUserDetailsToPatient(UserEntity user, Patient.PatientBuilder<?, ?> builder) {
        if (Objects.nonNull(user)) {
            builder
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .userId(user.getUserId())
                    .role(user.getRole());
        }
    }

    public PatientResponseDto createPatientResponseModel(PatientEntity entity) {
        return PatientResponseDto.builder()
                .patientId(entity.getPatientId())
                .userId(entity.getUser().getUserId())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .email(entity.getUser().getEmail()).build();
    }

}
