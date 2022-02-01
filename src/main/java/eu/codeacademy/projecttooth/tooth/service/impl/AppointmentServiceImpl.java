package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectTimeException;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.helper.AppointmentPageHelper;
import eu.codeacademy.projecttooth.tooth.mapper.AppointmentMapper;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.model.AppointmentSearchCriteria;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.AppointmentRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import eu.codeacademy.projecttooth.tooth.service.PatientService;
import eu.codeacademy.projecttooth.tooth.service.ServiceService;
import eu.codeacademy.projecttooth.tooth.specification.AppointmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorServiceAvailabilityService doctorServiceAvailabilityService;
    private final PatientService patientService;
    private final ServiceService serviceService;
    private final AppointmentSpecification specification;
    private final AppointmentPageHelper appointmentPageHelper;


    @Override
    public Page<Appointment> getAppointmentPageable(Long userId, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<AppointmentEntity> pageable;
        pageable = findAllAppointments(userId, page);
        return pageable.map(this::createAppointmentModel);
    }

    private Page<AppointmentEntity> findAllAppointments(Long userId, Pageable page) {
        Page<AppointmentEntity> pageable = appointmentRepository.findAllByPatientUserId(userId, page);
        return pageable;
    }

    @Override
    public Appointment getAppointmentAsPatient(Long appointmentId, Long userId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsPatient(appointmentId, userId)
                .map(appointmentMapper::createDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Get appointment not found by id:" + appointmentId));
    }

    @Override
    public Appointment getAppointmentAsDoctor(Long appointmentId, Long userId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsDoctor(appointmentId, userId)
                .map(appointmentMapper::createDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Get appointment not found by id:" + appointmentId));
    }

    @Override
    public Appointment updateAppointment(Long userId, ModifyAppointmentDto appointment) {
        AppointmentEntity appointmentEntity = updateEntity(userId, appointment);
        updateDatabase(appointmentEntity);
        return createAppointmentModel(appointmentEntity);
    }


    @Override
    public void deleteAppointment(Long userId, Long appointmentId) {
        AppointmentEntity appointmentEntity = getAppointmentEntity(userId, appointmentId);
        appointmentRepository.delete(appointmentEntity);
    }


    @Override
    public Appointment createAppointment(Long userId, ModifyAppointmentDto payload) {

        PatientEntity patient = getPatientEntity(userId);
        DoctorServiceAvailabilityEntity doctorServiceAvailability = getDoctorServiceAvailabilityEntity(payload);
        checkIfAppointmentTimeMatchesWithAvailability(payload, doctorServiceAvailability);
        reserveServiceAvailability(doctorServiceAvailability);
        AppointmentEntity appointmentEntity = appointmentMapper.createEntity(payload, patient, doctorServiceAvailability);
        updateDatabase(appointmentEntity);
        return createAppointmentModel(appointmentEntity);
    }


    @Override
    public void deleteExpiredAppointments() {
        appointmentRepository.deleteAllById(getExpiredAppointmentsId());
    }


//    @Override
//    public Page<Appointment> getAppointmentPageableAsDoctor(Long userId, int pageNumber, int pageSize) {
//        Pageable page = PageRequest.of(pageNumber, pageSize);
//        Page<AppointmentEntity> pageable = appointmentRepository.findAllByDoctorUserId(userId, page);
//        return pageable.map(this::createAppointmentModel);
//    }

    @Override
    public Page<Appointment> findAllAppointments(UserPrincipal principal, AppointmentSearchCriteria searchCriteria, AppointmentPageHelper pageHelper) {
        Pageable pageable = appointmentPageHelper.getPageable(pageHelper);
        Page<AppointmentEntity> appointments = findAppointmentsByRole(principal, searchCriteria, pageable);
        return appointments.map(this::createAppointmentModel);
    }

    private Page<AppointmentEntity> findAppointmentsByRole(UserPrincipal principal, AppointmentSearchCriteria searchCriteria, Pageable pageable) {
        Page<AppointmentEntity> appointments;
        if (principal.hasRole(RoleEnum.ROLE_PATIENT)) {
            Specification<AppointmentEntity> filter = specification.findAllWithFiltersForPatient(searchCriteria, principal.getUserId());
            appointments = appointmentRepository.findAll(filter, pageable);
        } else {
            Specification<AppointmentEntity> filter = specification.findAllWithFiltersForDoctor(searchCriteria, principal.getUserId());
            appointments = appointmentRepository.findAll(filter, pageable);
        }
        return appointments;
    }


    private void checkIfAppointmentTimeMatchesWithAvailability(ModifyAppointmentDto payload, DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        DoctorAvailabilityEntity doctorAvailability = doctorServiceAvailability.getDoctorAvailability();
        objectIsNotNull(doctorAvailability);
        checksIfAppointmentTimeIsCorrect(payload, doctorAvailability);
    }


    private AppointmentEntity updateEntity(Long userId, ModifyAppointmentDto appointmentDto) {

        AppointmentEntity appointment = getAppointmentEntity(userId, appointmentDto.getAppointmentId());
        appointment.setStartTime(appointmentDto.getStartTime());
        appointment.setEndTime(appointmentDto.getEndTime());
        ServiceEntity serviceEntity = getServiceEntity(appointmentDto);
        appointment.getDoctorServiceAvailability().setService(serviceEntity);
        return appointment;
    }

    private AppointmentEntity getAppointmentEntity(Long userId, Long appointmentId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsDoctor(appointmentId, userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'getAppointmentEntity' in AppointmentService with id:%s not found", appointmentId)));
    }

    private PatientEntity getPatientEntity(Long userId) {
        return patientService.getPatientEntity(userId);
    }

    private ServiceEntity getServiceEntity(ModifyAppointmentDto appointment) {
        Long serviceId = appointment.getServiceId();
        return serviceService.findServiceEntity(serviceId);
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(ModifyAppointmentDto payload) {
        return doctorServiceAvailabilityService.findDoctorServiceAvailabilityEntityById(payload.getDoctorServiceAvailabilityId());
    }


    private Iterable<Long> getExpiredAppointmentsId() {
        return () -> appointmentRepository.findAll()
                .stream()
                .filter(app -> app.getEndTime().isBefore(LocalDateTime.now(ZoneId.systemDefault())))
                .mapToLong(AppointmentEntity::getAppointmentId).iterator();
    }

    private void checksIfAppointmentTimeIsCorrect(ModifyAppointmentDto payload, DoctorAvailabilityEntity doctorAvailability) {
        if (!(payload.getStartTime().isEqual(doctorAvailability.getStartTime()) && payload.getEndTime().isEqual(doctorAvailability.getEndTime()))) {
            throw new IncorrectTimeException("Checking appointment time with availability time, one of them was incorrect");
        }
    }

    private void objectIsNotNull(DoctorAvailabilityEntity doctorAvailability) {
        if (!Objects.nonNull(doctorAvailability)) {
            throw new ObjectNotFoundException("Checking Appointment time with availability, DoctorAvailability entity not found");
        }
    }

    private Appointment createAppointmentModel(AppointmentEntity appointmentEntity) {
        return appointmentMapper.createDtoModel(appointmentEntity);
    }

    private AppointmentEntity updateDatabase(AppointmentEntity appointment) {
        return appointmentRepository.saveAndFlush(appointment);
    }

    private void reserveServiceAvailability(DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        doctorServiceAvailability.setReserved(true);
        doctorServiceAvailabilityService.updateDoctorServiceAvailabilityToReservedAndSaveToDatabase(doctorServiceAvailability);
    }
}
