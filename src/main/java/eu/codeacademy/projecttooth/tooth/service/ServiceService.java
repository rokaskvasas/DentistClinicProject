package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.model.Service;

import java.util.List;

public interface ServiceService {

    List<Service> getAllServices();

    ServiceEntity findServiceEntity(Long serviceId);
}
