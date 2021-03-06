package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.exception.DoctorAvailabilityReservedException;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectTimeException;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.AppointmentMapper;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.model.AppointmentSearchCriteria;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.AppointmentRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.*;
import eu.codeacademy.projecttooth.tooth.specification.AppointmentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final EmailService emailService;


    @Override
    public Appointment getAppointmentAsPatient(Long appointmentId, Long userId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsPatient(appointmentId, userId)
                .map(this::createAppointmentModel)
                .orElseThrow(() -> new ObjectNotFoundException("Appointment not found"));
    }

    @Override
    public Appointment getAppointmentAsDoctor(Long appointmentId, Long userId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsDoctor(appointmentId, userId)
                .map(appointmentMapper::createDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Appointment not found"));
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
        DoctorServiceAvailabilityEntity doctorServiceAvailabilityEntity = getDoctorServiceAvailabilityEntity(payload);
        checkIfServiceIsAvailable(doctorServiceAvailabilityEntity);
        checkIfAppointmentTimeMatchesWithAvailability(payload, doctorServiceAvailabilityEntity);
        reserveAvailability(doctorServiceAvailabilityEntity);
        AppointmentEntity appointmentEntity = createAppointmentEntity(payload, patient, doctorServiceAvailabilityEntity);
        updateDatabase(appointmentEntity);
        notifyDoctorAboutAppointment(doctorServiceAvailabilityEntity, appointmentEntity);
        return createAppointmentModel(appointmentEntity);
    }



    @Override
    public void deleteExpiredAppointments() {
        appointmentRepository.deleteAllById(getExpiredAppointmentsId());
    }


    @Override
    public Page<Appointment> findAllAppointments(UserPrincipal principal, AppointmentSearchCriteria searchCriteria, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
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
                .orElseThrow(() -> new ObjectNotFoundException(("AppointmentService not found")));
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

    private void reserveAvailability(DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        doctorServiceAvailability.getDoctorAvailability().setReserved(true);
        doctorServiceAvailabilityService.updateDoctorServiceAvailabilityToReservedAndSaveToDatabase(doctorServiceAvailability);
    }

    private AppointmentEntity createAppointmentEntity(ModifyAppointmentDto payload, PatientEntity patient, DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        return appointmentMapper.createEntity(payload, patient, doctorServiceAvailability);
    }
    private void notifyDoctorAboutAppointment(DoctorServiceAvailabilityEntity dsaEntity, AppointmentEntity appointment) {
        String doctorEmail = dsaEntity.getDoctorAvailability().getDoctorEntity().getUser().getEmail();
        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime endTime = appointment.getEndTime();
        emailService.send(doctorEmail, String.format("Appointment created from %s to %s", startTime, endTime), "Appointment");
    }


    private void checkIfServiceIsAvailable(DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        if (doctorServiceAvailability.getDoctorAvailability().isReserved()) {
            throw new DoctorAvailabilityReservedException("Doctor Availability is already reserved");
        }
    }

}
