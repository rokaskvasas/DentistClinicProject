package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.ServiceMapper;
import eu.codeacademy.projecttooth.tooth.model.Service;
import eu.codeacademy.projecttooth.tooth.repository.ServiceRepository;
import eu.codeacademy.projecttooth.tooth.service.ServiceService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    @Override
    public List<Service> getAllServices() {
        return repository.findAll().stream().map(mapper::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ServiceEntity findServiceEntity(Long serviceId) {
        return repository.findById(serviceId).orElseThrow(() -> new ObjectNotFoundException(String.format("Service by id:%s not found", serviceId)));
    }
}
