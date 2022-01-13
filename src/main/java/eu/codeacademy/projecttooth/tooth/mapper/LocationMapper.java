package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.model.Location;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationMapper {

    public Location createModel(LocationEntity entity){
        return Location.builder().locationId(entity.getLocationId())
                .name(entity.getName())
                .city(entity.getCity()).build();
    }
}
