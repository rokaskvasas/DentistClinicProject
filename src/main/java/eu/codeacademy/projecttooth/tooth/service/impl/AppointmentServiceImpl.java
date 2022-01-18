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
import eu.codeacademy.projecttooth.tooth.repository.PatientRepository;
import eu.codeacademy.projecttooth.tooth.repository.ServiceRepository;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorServiceAvailabilityRepository serviceAvailabilityRepository;
    private final ServiceRepository serviceRepository;


    @Override
    public Page<Appointment> getAppointmentPageable(Long userId, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<AppointmentEntity> pageable = appointmentRepository.findAllByPatientUserUserId(userId, page);
        return pageable.map(appointmentMapper::createDtoModel);
    }

    @Override
    public Appointment getAppointment(Long userId, Long appointmentId) {
        return appointmentRepository.findAllByPatientUserUserId(userId)
                .stream()
                .filter(app -> app.getAppointmentId().equals(appointmentId))
                .findAny()
                .map(appointmentMapper::createDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Get appointment not found by id:" + appointmentId));
    }

    @Override
    public Appointment updateAppointment(Long userId, ModifyAppointmentDto appointment) {
        return appointmentMapper.createDtoModel(appointmentRepository.saveAndFlush(updateEntity(userId, appointment)));
    }


    @Override
    public void deleteAppointment(Long userId, Long appointmentId) {
        appointmentRepository.delete(getAppointmentEntity(userId, appointmentId));
    }

    @Override
    public List<Appointment> getAppointmentList() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::createDtoModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteExpiredAppointments() {
        appointmentRepository.deleteAllById(getExpiredAppointmentsId());
    }


    @Override
    public Appointment createAppointment(Long userId, ModifyAppointmentDto payload) {

        PatientEntity patient = getPatientEntity(userId);
        DoctorServiceAvailabilityEntity doctorServiceAvailability = getDoctorServiceAvailabilityEntity(payload);
        checkIfAppointmentTimeMatchesWithAvailability(payload, doctorServiceAvailability);
        AppointmentEntity appointment = appointmentMapper.createEntity(payload, patient, doctorServiceAvailability);
        return appointmentMapper.createDtoModel(appointmentRepository.saveAndFlush(appointment));
    }

    private void checkIfAppointmentTimeMatchesWithAvailability(ModifyAppointmentDto payload, DoctorServiceAvailabilityEntity doctorServiceAvailability) {
        DoctorAvailabilityEntity doctorAvailability = doctorServiceAvailability.getDoctorAvailability();
        if (!Objects.nonNull(doctorAvailability)) {
            throw new ObjectNotFoundException("Checking Appointment time with availability, DoctorAvailability entity not found");
        }
        if (!(payload.getStartTime().isEqual(doctorAvailability.getStartTime()) && payload.getEndTime().isEqual(doctorAvailability.getEndTime()))) {
            throw new IncorrectTimeException("Checking appointment time with availability time, one of them was incorrect");
        }
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
        return appointmentRepository.findAllByPatientUserUserId(userId).stream()
                .filter(entity -> entity.getAppointmentId().equals(appointmentId))
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'getAppointmentEntity' in AppointmentService with id:%s not found", appointmentId)));
    }

    private PatientEntity getPatientEntity(Long userId) {
        return patientRepository.findByUserUserId(userId).orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'getPatientEntity' in AppointmentService with id: %s not found", userId)));
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
        return serviceAvailabilityRepository.findAllByDoctorAvailabilityDoctorEntityUserUserId(userId).stream()
                .anyMatch(entity -> entity.getDoctorAvailabilityServiceId().equals(doctorServiceAvailabilityId));
    }

    private Iterable<Long> getExpiredAppointmentsId() {
        return () -> appointmentRepository.findAll()
                .stream()
                .filter(app -> app.getEndTime().isBefore(LocalDateTime.now(ZoneId.systemDefault())))
                .mapToLong(AppointmentEntity::getAppointmentId).iterator();
    }
}
