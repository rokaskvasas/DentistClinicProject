package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityServiceEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.model.DoctorScheduler;
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

//    @Override
//    public List<DoctorScheduler> getAll(Doctor doctor) {
//        return serviceEntityRepository.findAll().stream().
//                filter(entity -> entity.getDoctorAvailabilityEntity().getDoctorEntity().getDoctorId().equals(doctor.getDoctorId()))
//                .map(this::createScheduler).collect(Collectors.toUnmodifiableList());
//    }


    public DoctorAvailabilityServiceEntity createEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return serviceEntityMapper.getEntity(doctorAvailabilityService);
    }

    public DoctorScheduler createScheduler(DoctorAvailabilityServiceEntity entity) {
        return DoctorScheduler.builder()
                .service(entity.getServiceEntity().getName())
                .startTime(entity.getDoctorAvailabilityEntity().getStartTime())
                .endTime(entity.getDoctorAvailabilityEntity().getEndTime())
                .build();
    }
}
