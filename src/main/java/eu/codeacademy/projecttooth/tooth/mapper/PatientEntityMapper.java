package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class PatientEntityMapper {

    public PatientEntity getPatientEntity(UserEntity userEntity){

        return PatientEntity.builder()
                .user(userEntity).build();
    }
}
