package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.AppointmentDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectDoctorForAppointmentException;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.AppointmentMapper;
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

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorServiceAvailabilityRepository serviceAvailabilityRepository;
    private final ServiceRepository serviceRepository;


    @Override
    public Page<AppointmentDto> getAppointmentPageable(Long userId, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<AppointmentEntity> pageable = appointmentRepository.findAllByPatientUserUserId(userId, page);
        return pageable.map(appointmentMapper::createDtoModel);
    }

    @Override
    public AppointmentDto getAppointment(Long userId, Long appointmentId) {
        return appointmentRepository.findAllByPatientUserUserId(userId)
                .stream()
                .filter(app -> app.getAppointmentId().equals(appointmentId))
                .findAny()
                .map(appointmentMapper::createDtoModel)
                .orElseThrow(() -> new ObjectNotFoundException("Get appointment not found by id:" + appointmentId));
    }

    @Override
    public void updateAppointment(Long userId, AppointmentDto appointment) {
        appointmentRepository.saveAndFlush(updateEntity(userId, appointment));

    }


    @Override
    public void deleteAppointment(Long userId, Long appointmentId) {

    }

    @Override
    public void createAppointment(Long userId, DoctorServiceAvailabilityDto payload) {
        PatientEntity patient = getPatientEntity(userId);
        DoctorServiceAvailabilityEntity doctorServiceAvailability = getDoctorServiceAvailabilityEntity(payload);
        AppointmentEntity appointment = appointmentMapper.createEntity(payload, patient, doctorServiceAvailability);
        appointmentRepository.saveAndFlush(appointment);
    }


    private AppointmentEntity updateEntity(Long userId, AppointmentDto appointmentDto) {

        AppointmentEntity appointment = getAppointmentEntity(appointmentDto);
        if (!checkIfAppointmentDoctorIsCorrect(userId, appointment.getDoctorServiceAvailability().getDoctorAvailabilityServiceId())) {
            throw new IncorrectDoctorForAppointmentException("Wrong doctor for this appointment id: " + appointmentDto.getAppointmentId());
        }
        appointment.setStartTime(appointmentDto.getStartTime());
        appointment.setEndTime(appointmentDto.getEndTime());
        ServiceEntity serviceEntity = getServiceEntity(appointmentDto);
        appointment.getDoctorServiceAvailability().setService(serviceEntity);
        return appointment;
    }

    private AppointmentEntity getAppointmentEntity(AppointmentDto appointmentDto) {
        return appointmentRepository.findAll().stream()
                .filter(entity -> entity.getAppointmentId().equals(appointmentDto.getAppointmentId()))
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'updateEntity' in appointment service, entity by id:%s not found", appointmentDto.getAppointmentId())));
    }

    private PatientEntity getPatientEntity(Long userId) {
        return patientRepository.findByUserUserId(userId).orElseThrow(() -> new ObjectNotFoundException(String.format("Creating appointment user with id: %s not found", userId)));
    }

    private ServiceEntity getServiceEntity(AppointmentDto appointmentDto) {
        return serviceRepository.findById(appointmentDto.getServiceEnum().getService())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Method 'updateEntity' in appointment service, service entity by id:%s not found", appointmentDto.getServiceEnum().getService())));
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(DoctorServiceAvailabilityDto payload) {
        return serviceAvailabilityRepository.findById(payload.getDoctorServiceAvailabilityId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Creating appointment doctor service availability with id:%s not found ", payload.getDoctorServiceAvailabilityId())));
    }


    private boolean checkIfAppointmentDoctorIsCorrect(Long userId, Long doctorServiceAvailabilityId) {
        return serviceAvailabilityRepository.findAllByDoctorAvailabilityDoctorEntityUserUserId(userId).stream()
                .anyMatch(entity -> entity.getDoctorAvailabilityServiceId().equals(doctorServiceAvailabilityId));
    }
}
