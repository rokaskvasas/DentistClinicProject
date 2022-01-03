package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.exception.IdNotFoundException;
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

    private final DoctorAvailabilityEntityMapper entityMapper;
    private final DoctorAvailabilityEntityRepository availabilityEntityRepository;

    @Override
    public void createAvailability(List<DoctorAvailability> doctorAvailabilityList) {

        availabilityEntityRepository.saveAllAndFlush(doctorAvailabilityList.stream().map(this::createEntity).collect(Collectors.toUnmodifiableList()));

    }

    @Override
    public List<DoctorAvailability> getAvailabilityList(Long doctorId) {

        return availabilityEntityRepository.findAllByDoctorEntityDoctorId(doctorId).stream().map(this::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateAvailability(DoctorAvailability doctorAvailability) {
        availabilityEntityRepository.saveAndFlush(entityMapper.updateEntity(doctorAvailability));
    }

    public DoctorAvailabilityEntity createEntity(DoctorAvailability doctorAvailability) {
        return entityMapper.createEntity(doctorAvailability);
    }

    public DoctorAvailability createModel(DoctorAvailabilityEntity entity) {
        return entityMapper.createModel(entity);
    }
}
