package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectDoctorAvailabilityTime;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    private final DoctorAvailabilityMapper mapper;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public DoctorAvailability getAvailability(Long availabilityId, Long userId) {
        return availabilityRepository.findAllByDoctorEntityUserUserId(userId)
                .stream().filter(e -> e.getDoctorAvailabilityId().equals(availabilityId))
                .findAny()
                .map(mapper::createModel)
                .orElseThrow(() -> new ObjectNotFoundException("Availability not found, id: " + availabilityId));
    }

    @Override
    public List<DoctorAvailability> getAvailabilityList(UserPrincipal principal) {
        return availabilityRepository.findAllByDoctorEntityUserUserId(principal.getUserId())
                .stream()
                .map(mapper::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void createAvailability(List<DoctorAvailability> doctorAvailabilityList, Long userId) {
        doctorAvailabilityList.forEach(this::availabilityTimeCheck);
        availabilityRepository.saveAllAndFlush(doctorAvailabilityList
                .stream()
                .map(doctorAvailability -> mapper.createEntity(doctorAvailability, getDoctorEntity(userId)))
                .collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public void updateAvailability(DoctorAvailability doctorAvailability, Long userId) {
        availabilityRepository.saveAndFlush(updateEntity(doctorAvailability, userId));
    }

    @Override
    @Transactional
    public void deleteAvailability(Long doctorAvailabilityId, Long userId) {
        availabilityRepository.delete(getDoctorAvailabilityEntity(doctorAvailabilityId, userId));
    }

    public DoctorAvailabilityEntity updateEntity(DoctorAvailability doctorAvailability, Long userId) {
        availabilityTimeCheck(doctorAvailability);
        DoctorAvailabilityEntity entity = getDoctorAvailabilityEntity(doctorAvailability.getDoctorAvailabilityId(), userId);
        entity.setStartTime(doctorAvailability.getStartTime());
        entity.setEndTime(doctorAvailability.getEndTime());
        return entity;
    }

    private DoctorEntity getDoctorEntity(Long userId) {
        return doctorRepository.findDoctorEntityByUserUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("DoctorEntity not found by id: %s", userId)));
    }


    public DoctorAvailabilityEntity getDoctorAvailabilityEntity(Long doctorAvailabilityId, Long userId) {
        return availabilityRepository.findAllByDoctorEntityUserUserId(userId)
                .stream()
                .filter(entity -> entity.getDoctorAvailabilityId().equals(doctorAvailabilityId))
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found", userId)));
    }

    private void availabilityTimeCheck(DoctorAvailability doctorAvailability) {
        if (!startAndEndTimeIsCorrect(doctorAvailability) || !timeIsInSameDay(doctorAvailability)) {
            throw new IncorrectDoctorAvailabilityTime("StartTime or endTime is incorrect");
        }
    }

    private boolean timeIsInSameDay(DoctorAvailability doctorAvailability) {
        return doctorAvailability.getStartTime().toLocalDate().isEqual(doctorAvailability.getEndTime().toLocalDate());
    }

    private boolean startAndEndTimeIsCorrect(DoctorAvailability doctorAvailability) {
        return doctorAvailability.getStartTime().isBefore(doctorAvailability.getEndTime());
    }

}
