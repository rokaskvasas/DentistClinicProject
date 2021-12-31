package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityServiceEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityServiceEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceEntityServiceImpl implements DoctorAvailabilityServiceEntityService {

    private final DoctorAvailabilityServiceEntityMapper serviceEntityMapper;
    private final DoctorAvailabilityServiceEntityRepository serviceEntityRepository;

    @Override
    public void createService(List<DoctorAvailabilityService> doctorAvailabilityServiceList) {

        serviceEntityRepository.saveAllAndFlush(doctorAvailabilityServiceList.stream().map(this::createEntity).collect(Collectors.toUnmodifiableList()));

    }

    public DoctorAvailabilityServiceEntity createEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return serviceEntityMapper.getEntity(doctorAvailabilityService);
    }
}
