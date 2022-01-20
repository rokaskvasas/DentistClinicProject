package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.model.Location;

import java.util.List;

public interface LocationService {

    List<Location> getAllLocations();

    LocationEntity getLocationEntity(Long locationId);
}
