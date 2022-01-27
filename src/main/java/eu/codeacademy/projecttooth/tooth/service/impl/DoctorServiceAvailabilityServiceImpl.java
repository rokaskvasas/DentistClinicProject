package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityResponse;
import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.QualificationException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorServiceAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorServiceAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import eu.codeacademy.projecttooth.tooth.service.ServiceService;
import eu.codeacademy.projecttooth.tooth.specification.DoctorServiceAvailabilitySpecification;
import eu.codeacademy.projecttooth.tooth.test.DSAPage;
import eu.codeacademy.projecttooth.tooth.test.DSASearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class DoctorServiceAvailabilityServiceImpl implements DoctorServiceAvailabilityService {

    private final DoctorServiceAvailabilityMapper mapper;
    private final DoctorServiceAvailabilityRepository availabilityServiceRepository;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final ServiceService serviceService;
    private final DoctorServiceAvailabilitySpecification specifications;


    @Override
    public Page<DoctorServiceAvailabilityResponse> getAvailabilityServiceAsPage(UserPrincipal principal, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorServiceAvailabilityEntity> pageable;
        pageable = findDoctorServiceAvailabilityEntitiesByRole(principal, page);
        return pageable.map(this::createDoctorServiceAvailabilityResponseModel);
    }

    @Override
    public Page<DoctorServiceAvailability> getAvailabilityServiceAsPatientAndAdmin(UserPrincipal principal, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorServiceAvailabilityEntity> pageable = findDoctorServiceAvailabilityEntitiesByRole(principal, page);
        return pageable.map(this::createDoctorServiceAvailabilityModel);
    }

    @Override
    public Page<DoctorServiceAvailability> findAvailableDoctorServiceAvailablities(String city, ServiceEnum service, LocalDateTime startTime, LocalDateTime endTime, String doctorFirstName, String doctorLastName, DSAPage dsaPage) {
        Pageable page = PageRequest.of(dsaPage.getPageNumber(), dsaPage.getPageSize());
        Page<DoctorServiceAvailabilityEntity> pageable = availabilityServiceRepository
                .findAll(specifications.findAllWithFilters(city, service, startTime, endTime, doctorFirstName, doctorLastName), page);
        return pageable.map(this::createDoctorServiceAvailabilityModel);
    }

    @Override
    public Page<DoctorServiceAvailability> testWithSearch(DSAPage dsaPage, DSASearchCriteria dsaSearchCriteria) {
        Pageable page = getPageable(dsaPage);
        Page<DoctorServiceAvailabilityEntity> pageable = availabilityServiceRepository.findAll(specifications.findAllWithFiltersTEST(dsaSearchCriteria), page);
        return pageable.map(this::createDoctorServiceAvailabilityModel);
    }

    private Pageable getPageable(DSAPage dsaPage) {
        Sort sort = Sort.by(dsaPage.getSortDirection(), dsaPage.getSortBy());
        return PageRequest.of(dsaPage.getPageNumber(), dsaPage.getPageSize(), sort);
    }


    @Override
    public DoctorServiceAvailabilityResponse getAvailabilityService(UserPrincipal principal, Long availabilityServiceId) {
        return getDoctorServiceAvailability(principal, availabilityServiceId);
    }

    private DoctorServiceAvailabilityResponse createDoctorServiceAvailabilityResponseModel(DoctorServiceAvailabilityEntity entity) {
        return mapper.createResponseModel(entity);
    }


    @Override
    public DoctorServiceAvailabilityEntity findDoctorServiceAvailabilityEntity(Long userId, Long availabilityServiceId) {
        return availabilityServiceRepository.findByUserAndServiceAvailabilityId(userId, availabilityServiceId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability service entity by id:%s not found", availabilityServiceId)));
    }


    @Override
    public void deleteExpiredServiceAvailability() {
        availabilityServiceRepository.deleteAllById(getExpiredServices());
    }

    @Override
    public void updateDoctorServiceAvailabilityToReservedAndSaveToDatabase(DoctorServiceAvailabilityEntity entity) {
        updateDatabase(entity);
    }


    @Override
    public DoctorServiceAvailabilityResponse createAvailabilityService(ModifyDoctorServiceAvailabilityDto serviceAvailability, Long userId) {
        isQualified(serviceAvailability, userId);
        DoctorServiceAvailabilityEntity doctorAvailabilityServiceEntity = createDoctorAvailabilityServiceEntity(serviceAvailability, userId);
        updateDatabase(doctorAvailabilityServiceEntity);
        return createDoctorServiceAvailabilityResponseModel(doctorAvailabilityServiceEntity);
    }

    @Override
    public DoctorServiceAvailability updateAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        DoctorServiceAvailabilityEntity doctorServiceAvailabilityEntity = updateEntity(doctorServiceAvailability, userId);
        updateDatabase(doctorServiceAvailabilityEntity);
        return createDoctorServiceAvailabilityModel(doctorServiceAvailabilityEntity);
    }

    @Override
    @Transactional
    public Long deleteAvailabilityService(Long serviceId, UserPrincipal principal) {
        DoctorServiceAvailabilityEntity doctorServiceAvailability = findDoctorServiceAvailabilityEntityById(serviceId);
        deleteDoctorAvailabilityServiceByRoleAndServiceId(doctorServiceAvailability.getDoctorAvailabilityServiceId(), principal);
        return serviceId;
    }

    private DoctorServiceAvailabilityEntity findDoctorServiceAvailabilityEntityById(Long serviceId) {
        return availabilityServiceRepository.findById(serviceId).orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor service availability by id:%s not found", serviceId)));
    }

    private void deleteDoctorAvailabilityServiceByRoleAndServiceId(Long serviceId, UserPrincipal principal) {
        if (principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            availabilityServiceRepository.deleteById(serviceId);
        } else {
            availabilityServiceRepository.delete(getDoctorServiceAvailabilityEntity(serviceId, principal.getUserId()));
        }
    }


    private void isQualified(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        int requiredQualification = getServiceEntity(doctorServiceAvailability.getServiceId()).getMinimumQualification().getCourse();
        int currentQualification = doctorAvailabilityService.getDoctorAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityId(), userId).getDoctorEntity().getQualification().getCourse();

        if ((requiredQualification > currentQualification)) {
            throw new QualificationException(("Minimum qualification requirements not met."));
        }
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntityByUserID(Long availabilityId, Long userId) {
        return doctorAvailabilityService.findDoctorAvailabilityEntityByUserAndAvailabilityId(availabilityId, userId);
    }

    private ServiceEntity getServiceEntity(Long serviceId) {
        return serviceService.findServiceEntity(serviceId);
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(Long serviceAvailabilityId, Long userId) {
        return availabilityServiceRepository.findByUserAndServiceAvailabilityId(userId, serviceAvailabilityId)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor Service availability entity not found id:" + serviceAvailabilityId));
    }


    private DoctorServiceAvailabilityEntity updateEntity(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        isQualified(doctorServiceAvailability, userId);
        DoctorServiceAvailabilityEntity entity = getDoctorServiceAvailabilityEntity(doctorServiceAvailability.getDoctorServiceAvailabilityId(), userId);
        entity.setService(getServiceEntity(doctorServiceAvailability.getServiceId()));
        return entity;

    }

    private Iterable<Long> getExpiredServices() {
        return () -> availabilityServiceRepository.findAll()
                .stream()
                .filter(service -> service.getDoctorAvailability().getEndTime().isBefore(LocalDateTime.now(ZoneId.systemDefault())))
                .mapToLong(DoctorServiceAvailabilityEntity::getDoctorAvailabilityServiceId).iterator();
    }

    private Page<DoctorServiceAvailabilityEntity> findDoctorServiceAvailabilityEntitiesByRole(UserPrincipal principal, Pageable page) {
        Page<DoctorServiceAvailabilityEntity> pageable;
        if (principal.hasRole(RoleEnum.ROLE_PATIENT) || principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            pageable = availabilityServiceRepository.findAllAvailable(page);
        } else {
            pageable = availabilityServiceRepository.findAllByUserId(principal.getUserId(), page);
        }
        return pageable;
    }

    private DoctorServiceAvailability createDoctorServiceAvailabilityModel(DoctorServiceAvailabilityEntity entity) {
        return mapper.createModel(entity);
    }

    private DoctorServiceAvailabilityEntity createDoctorAvailabilityServiceEntity(ModifyDoctorServiceAvailabilityDto serviceAvailability, Long userId) {
        Long doctorAvailabilityId = serviceAvailability.getDoctorAvailabilityId();
        ServiceEntity serviceEntity = getServiceEntity(serviceAvailability.getServiceId());
        DoctorAvailabilityEntity doctorAvailabilityEntity = getDoctorAvailabilityEntityByUserID(doctorAvailabilityId, userId);
        DoctorServiceAvailabilityEntity doctorServiceAvailabilityEntity = createDoctorServiceAvailabilityEntity(serviceEntity);
        doctorServiceAvailabilityEntity.setDoctorAvailability(doctorAvailabilityEntity);
        return doctorServiceAvailabilityEntity;
    }

    private DoctorServiceAvailabilityEntity updateDatabase(DoctorServiceAvailabilityEntity entity) {
        return availabilityServiceRepository.saveAndFlush(entity);
    }

    private DoctorServiceAvailabilityEntity createDoctorServiceAvailabilityEntity(ServiceEntity serviceEntity) {
        return mapper.createEntity(serviceEntity);
    }

    private DoctorServiceAvailabilityResponse getDoctorServiceAvailability(UserPrincipal principal, Long availabilityServiceId) {
        DoctorServiceAvailabilityResponse DoctorServiceAvailabilityResponse;
        if (principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            DoctorServiceAvailabilityResponse = availabilityServiceRepository
                    .findById(availabilityServiceId)
                    .map(this::createDoctorServiceAvailabilityResponseModel)
                    .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor service availability by id:%s not found", availabilityServiceId)));
        } else {
            DoctorServiceAvailabilityResponse = availabilityServiceRepository.findByUserAndServiceAvailabilityId(principal.getUserId(), availabilityServiceId)
                    .map(this::createDoctorServiceAvailabilityResponseModel)
                    .orElseThrow(() -> new ObjectNotFoundException("Doctor Availability service by id not found: " + availabilityServiceId));
        }
        return DoctorServiceAvailabilityResponse;
    }
}
