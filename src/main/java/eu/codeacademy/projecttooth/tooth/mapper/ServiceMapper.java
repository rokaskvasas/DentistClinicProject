package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
public class ServiceMapper {

    public eu.codeacademy.projecttooth.tooth.model.Service createModel(ServiceEntity entity){
        return eu.codeacademy.projecttooth.tooth.model.Service.builder()
                .serviceId(entity.getServiceId())
                .service(entity.getName())
                .minimumQualification(entity.getMinimumQualification()).build();
    }

}
