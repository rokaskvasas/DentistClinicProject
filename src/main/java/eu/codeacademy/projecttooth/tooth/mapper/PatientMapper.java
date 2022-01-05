package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {

    public PatientEntity createEntity(UserEntity userEntity){

        return PatientEntity.builder()
                .user(userEntity).build();
    }
    public Patient createModel(PatientEntity entity){
        return Patient.builder()
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber()).build();
    }

}
