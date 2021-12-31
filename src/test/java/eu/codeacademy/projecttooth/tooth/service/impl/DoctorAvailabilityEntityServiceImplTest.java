package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class DoctorAvailabilityEntityServiceImplTest {
//
//    @InjectMocks
//    DoctorAvailabilityEntityServiceImpl service;
//
//    @Mock
//    DoctorAvailabilityEntityRepository repository;
//
//
//    @Test
//    void createAvailability() {
//
////        try{
////            service.createAvailability(List.of(
////                    new DoctorAvailability(1L, LocalDateTime.of(2020, Month.DECEMBER,12,10,10), LocalDateTime.now(),4L)));
////        }catch (IllegalArgumentException e){
////            Assertions.assertNotNull(e);
////        }
//        ArgumentCaptor<DoctorAvailabilityEntity> argumentCaptor = ArgumentCaptor.forClass(DoctorAvailabilityEntity.class);
//        verify(repository).save(argumentCaptor.capture());
//
//        DoctorAvailabilityEntity entity = new DoctorAvailabilityEntity();
//    }
//}