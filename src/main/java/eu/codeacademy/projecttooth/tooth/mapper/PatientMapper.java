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
        Patient.PatientBuilder<?, ?> builder = Patient.builder()
                .patientId(entity.getPatientId());
        setUserDetailsToPatient(builder, entity.getUser());
        return builder.build();
    }

    private void setUserDetailsToPatient(Patient.PatientBuilder<?, ?> builder, UserEntity user) {
        if(Objects.nonNull(user)){
            builder
                    .userId(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber()).build();
        }
    }

}
