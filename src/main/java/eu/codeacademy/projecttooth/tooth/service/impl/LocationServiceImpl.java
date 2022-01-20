package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.LocationMapper;
import eu.codeacademy.projecttooth.tooth.model.Location;
import eu.codeacademy.projecttooth.tooth.repository.LocationRepository;
import eu.codeacademy.projecttooth.tooth.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Override
    public List<Location> getAllLocations() {
        return repository.findAll().stream().map(mapper::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public LocationEntity getLocationEntity(Long locationId) {
        return repository.findById(locationId).orElseThrow(() -> new ObjectNotFoundException(String.format("Location by id: %s not found", locationId)));
    }
}
