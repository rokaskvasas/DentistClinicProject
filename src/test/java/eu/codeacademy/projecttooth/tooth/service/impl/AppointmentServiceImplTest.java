//package eu.codeacademy.projecttooth.tooth.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
//import eu.codeacademy.projecttooth.tooth.mapper.AppointmentMapper;
//import eu.codeacademy.projecttooth.tooth.repository.AppointmentRepository;
//import eu.codeacademy.projecttooth.tooth.repository.DoctorServiceAvailabilityRepository;
//import eu.codeacademy.projecttooth.tooth.repository.PatientRepository;
//import eu.codeacademy.projecttooth.tooth.repository.ServiceRepository;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = {AppointmentServiceImpl.class})
//@ExtendWith(SpringExtension.class)
//class AppointmentServiceImplTest {
//    @MockBean
//    private AppointmentMapper appointmentMapper;
//
//    @MockBean
//    private AppointmentRepository appointmentRepository;
//
//    @Autowired
//    private AppointmentServiceImpl appointmentServiceImpl;
//
//    @MockBean
//    private DoctorServiceAvailabilityRepository doctorServiceAvailabilityRepository;
//
//    @MockBean
//    private PatientRepository patientRepository;
//
//    @MockBean
//    private ServiceRepository serviceRepository;
//
//    @Test
//    void GetAppointmentPageable() {
//        when(appointmentRepository.findAllByPatientUserId(anyLong(),any(PageRequest.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
//        assertTrue(appointmentServiceImpl.getAppointmentPageable(123L, 10, 3).toList().isEmpty());
//        verify(appointmentRepository).findAllByPatientUserId(anyLong(),any(PageRequest.class));
//        assertTrue(appointmentServiceImpl.getAppointmentList().isEmpty());
//    }
//
//    @Test
//    void GetAppointmentPageableThrowsException() {
//        when(appointmentRepository.findAllByPatientUserId(anyLong(),any(PageRequest.class))).thenThrow(new ObjectNotFoundException("Msg"));
//        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> appointmentServiceImpl.getAppointmentPageable(123L, 10, 3));
//        assertEquals("Msg",exception.getMessage());
//        verify(appointmentRepository).findAllByPatientUserId(anyLong(),any(PageRequest.class));
//    }
//}
//
