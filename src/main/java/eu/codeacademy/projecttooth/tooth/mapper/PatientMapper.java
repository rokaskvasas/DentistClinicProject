package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PatientMapper {

    public PatientEntity createEntity(UserEntity userEntity){

        return PatientEntity.builder()
                .user(userEntity).build();
    }
    public Patient createModel(PatientEntity entity){
        Patient.PatientBuilder<?, ?> builder = Patient.builder();
        setUserDetailsToPatient(entity.getUser(), builder);
        return builder.patientId(entity.getPatientId()).build();
    }

    private void setUserDetailsToPatient(UserEntity user, Patient.PatientBuilder<?, ?> builder) {
        if(Objects.nonNull(user)){
            builder
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .userId(user.getUserId())
                    .role(user.getRole());
        }
    }

}
