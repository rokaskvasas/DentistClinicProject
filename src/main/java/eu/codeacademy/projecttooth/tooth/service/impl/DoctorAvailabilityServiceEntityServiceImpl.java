package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityServiceEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.model.DoctorScheduler;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityServiceEntityRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceEntityServiceImpl implements DoctorAvailabilityServiceEntityService {

    private final DoctorAvailabilityServiceEntityMapper entityMapper;
    private final DoctorAvailabilityServiceEntityRepository serviceEntityRepository;

    @Override
    public void createAvailabilityService(List<DoctorAvailabilityService> doctorAvailabilityServiceList) {

        serviceEntityRepository.saveAllAndFlush(doctorAvailabilityServiceList.stream().map(this::createEntity).collect(Collectors.toUnmodifiableList()));

    }

    @Override
    public List<DoctorAvailabilityService> getAvailabilityServiceList(UserPrincipal principal) {
        return serviceEntityRepository
                .findAllByDoctorAvailabilityEntity_DoctorEntity_User_UserId(principal.getUserId())
                .stream().map(this::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateAvailabilityService(DoctorAvailabilityService doctorAvailabilityService) {
        serviceEntityRepository.saveAndFlush(updateEntity(doctorAvailabilityService));
    }

    @Override
    @Transactional
    public void deleteAvailabilityService(DoctorAvailabilityService doctorAvailabilityService) {
        serviceEntityRepository.removeByDoctorAvailabilityServiceId(doctorAvailabilityService.getDoctorAvailabilityServiceId());
    }


    private DoctorAvailabilityServiceEntity createEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return entityMapper.createEntity(doctorAvailabilityService);
    }

    private DoctorAvailabilityServiceEntity updateEntity(DoctorAvailabilityService doctorAvailabilityService){
        return  entityMapper.updateEntity(doctorAvailabilityService);
    }

    private DoctorAvailabilityService createModel(DoctorAvailabilityServiceEntity entity){
       return entityMapper.createModel(entity);
    }




    //    @Override
//    public List<DoctorScheduler> getAll(Doctor doctor) {
//        return serviceEntityRepository.findAll().stream().
//                filter(entity -> entity.getDoctorAvailabilityEntity().getDoctorEntity().getDoctorId().equals(doctor.getDoctorId()))
//                .map(this::createScheduler).collect(Collectors.toUnmodifiableList());
//    }



    public DoctorScheduler createScheduler(DoctorAvailabilityServiceEntity entity) {
        return DoctorScheduler.builder()
                .service(entity.getServiceEntity().getName())
                .startTime(entity.getDoctorAvailabilityEntity().getStartTime())
                .endTime(entity.getDoctorAvailabilityEntity().getEndTime())
                .build();
    }
}
