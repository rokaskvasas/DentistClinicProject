package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.exception.IdNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityEntityRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityEntityServiceImpl implements DoctorAvailabilityEntityService {

    private final DoctorAvailabilityEntityMapper entityMapper;
    private final DoctorAvailabilityEntityRepository availabilityEntityRepository;

    @Override
    public void createAvailability(List<DoctorAvailability> doctorAvailabilityList) {
        availabilityEntityRepository.saveAllAndFlush(doctorAvailabilityList.stream()
                .map(entityMapper::createEntity).collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public void updateAvailability(DoctorAvailability doctorAvailability) {
        availabilityEntityRepository.saveAndFlush(entityMapper.updateEntity(doctorAvailability));
    }

    @Override
    public List<DoctorAvailability> getAvailabilityList(UserPrincipal principal) {
        return availabilityEntityRepository.findAllByDoctorEntityUserUserId(principal.getUserId()).stream()
                .map(entityMapper::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public void deleteAvailability(DoctorAvailability doctorAvailability) {
        availabilityEntityRepository.removeByDoctorAvailabilityId(doctorAvailability.getDoctorAvailabilityId());
    }

}
