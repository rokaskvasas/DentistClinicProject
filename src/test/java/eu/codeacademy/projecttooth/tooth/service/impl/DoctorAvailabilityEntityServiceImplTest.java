package eu.codeacademy.projecttooth.tooth.service.impl;

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