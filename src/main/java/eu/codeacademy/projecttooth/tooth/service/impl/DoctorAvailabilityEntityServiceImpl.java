package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityEntityServiceImpl implements DoctorAvailabilityEntityService {

    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;
    private final DoctorAvailabilityEntityRepository doctorAvailabilityEntityRepository;

    @Override
    public void createAvailability(List<DoctorAvailability> doctorAvailabilities) {

        doctorAvailabilityEntityRepository.saveAllAndFlush(doctorAvailabilities.stream().map(this::createEntity).collect(Collectors.toUnmodifiableList()));

    }

    public DoctorAvailabilityEntity createEntity(DoctorAvailability doctorAvailability) {
        return doctorAvailabilityEntityMapper.getEntity(doctorAvailability);
    }
}
