package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectDoctorForAppointmentException;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectTimeException;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.AppointmentMapper;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.repository.AppointmentRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorServiceAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.ServiceRepository;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import eu.codeacademy.projecttooth.tooth.service.PatientService;
import eu.codeacademy.projecttooth.tooth.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorServiceAvailabilityRepository serviceAvailabilityRepository;
    private final ServiceRepository serviceRepository;
    private final PatientService patientService;
    private final ServiceService serviceService;


    @Override
    public Page<Appointment> getAppointmentPageable(Long userId, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<AppointmentEntity> pageable = appointmentRepository.findAllByPatientUserId(userId, page);
        return pageable.map(appointmentMapper::createDtoModel);
    }

    @Override
    public Appointment getAppointmentAsPatient(Long appointmentId, Long userId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsPatient(appointmentId, userId)
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

        appointmentRepository.delete(getAppointmentEntity(userId, appointmentId));
    }


    @Override
    public Appointment createAppointment(Long userId, ModifyAppointmentDto payload) {

        PatientEntity patient = getPatientEntity(userId);
        DoctorServiceAvailabilityEntity doctorServiceAvailability = getDoctorServiceAvailabilityEntity(payload);
        checkIfAppointmentTimeMatchesWithAvailability(payload, doctorServiceAvailability);
        reserveServiceAvailability(doctorServiceAvailability);
        AppointmentEntity appointment = appointmentMapper.createEntity(payload, patient, doctorServiceAvailability);
        return appointmentMapper.createDtoModel(updateDatabase(appointment));
    }

    private AppointmentEntity updateDatabase(AppointmentEntity appointment) {
        return appointmentRepository.saveAndFlush(appointment);
    }

    private void reserveServiceAvailability(DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        doctorServiceAvailability.setReserved(true);
        serviceAvailabilityRepository.saveAndFlush(doctorServiceAvailability);
    }

    @Override
    public void deleteExpiredAppointments() {
        appointmentRepository.deleteAllById(getExpiredAppointmentsId());
    }

    @Override
    public Appointment getAppointmentAsDoctor(Long appointmentId, Long userId) {
        return appointmentRepository.findByAppointmentIdAndUserIdAsDoctor(appointmentId, userId)
                .map(appointmentMapper::createDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Get appointment not found by id:" + appointmentId));
    }

    @Override
    public Page<Appointment> getAppointmentPageableAsDoctor(Long userId, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<AppointmentEntity> pageable = appointmentRepository.findAllByDoctorUserId(userId, page);
        return pageable.map(appointmentMapper::createDtoModel);
    }

    private void checkIfAppointmentTimeMatchesWithAvailability(ModifyAppointmentDto payload, DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        DoctorAvailabilityEntity doctorAvailability = doctorServiceAvailability.getDoctorAvailability();
        objectIsNotNull(doctorAvailability);
        checksIfAppointmentTimeIsCorrect(payload, doctorAvailability);
    }


    private AppointmentEntity updateEntity(Long userId, ModifyAppointmentDto appointmentDto) {

        AppointmentEntity appointment = getAppointmentEntity(userId, appointmentDto.getAppointmentId());
        if (!checkIfAppointmentDoctorIsCorrect(userId, appointment.getDoctorServiceAvailability().getDoctorAvailabilityServiceId())) {
            throw new IncorrectDoctorForAppointmentException("Wrong doctor for this appointment id: " + appointmentDto.getAppointmentId());
        }
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
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'updateEntity' in AppointmentService with id:%s not found", serviceId)));
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(ModifyAppointmentDto payload) {
        return serviceAvailabilityRepository.findById(payload.getDoctorServiceAvailabilityId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'getDoctorServiceAvailabilityEntity' in AppointmentService with id:%s not found ", payload.getDoctorServiceAvailabilityId())));
    }

    private boolean checkIfAppointmentDoctorIsCorrect(Long userId, Long doctorServiceAvailabilityId) {
        return serviceAvailabilityRepository.findByUserAndServiceAvailabilityId(userId, doctorServiceAvailabilityId).isPresent();
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
}
