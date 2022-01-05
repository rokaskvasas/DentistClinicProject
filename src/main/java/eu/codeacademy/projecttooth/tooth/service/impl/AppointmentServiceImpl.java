package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.AppointmentDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.AppointmentMapper;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorServiceAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.mapper.PatientMapper;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.repository.AppointmentRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorServiceAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.PatientRepository;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorServiceAvailabilityRepository serviceAvailabilityRepository;

    @Override
    public void createAppointment(Long userId, DoctorServiceAvailabilityDto payload) {
        PatientEntity patient = getPatientEntity(userId);
        DoctorServiceAvailabilityEntity doctorServiceAvailability = getDoctorServiceAvailabilityEntity(payload);
        AppointmentEntity appointment = appointmentMapper.createEntity(payload, patient, doctorServiceAvailability);
        appointmentRepository.saveAndFlush(appointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentList(Long userId) {
        return appointmentRepository.findAllByPatientUserUserId(userId).stream().map(appointmentMapper::createDtoModel).collect(Collectors.toUnmodifiableList());

    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(DoctorServiceAvailabilityDto payload) {
        return serviceAvailabilityRepository.findById(payload.getDoctorServiceAvailabilityId())
                .orElseThrow(()-> new ObjectNotFoundException(String.format("Creating appointment doctor service availability with id:%s not found ", payload.getDoctorServiceAvailabilityId())));
    }

    private PatientEntity getPatientEntity(Long userId) {
        return patientRepository.findByUserUserId(userId).orElseThrow(() -> new ObjectNotFoundException(String.format("Creating appointment user with id: %s not found", userId)));
    }
}
