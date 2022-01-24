package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorMapper;
import eu.codeacademy.projecttooth.tooth.mapper.LocationMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {DoctorAvailabilityMapper.class, DoctorMapper.class, LocationMapper.class})
@ExtendWith(SpringExtension.class)
class DoctorAvailabilityServiceImplTest {


    public static final long USER_ID = 19L;
    public static final long ADMIN_ID = 1L;
    public static final long DOCTOR_AVAILABILITY_ID = 12L;
    public final UserPrincipal adminPrincipal = createUserPrincipal(RoleEnum.ROLE_ADMIN, ADMIN_ID);
    public final UserPrincipal doctorPrincipal = createUserPrincipal(RoleEnum.ROLE_DOCTOR, USER_ID);

    @InjectMocks
    private DoctorAvailabilityServiceImpl doctorAvailabilityService;
    @Mock
    private DoctorAvailabilityMapper mapper;

    @Captor
    private ArgumentCaptor<DoctorAvailability> availabilityArgument = ArgumentCaptor.forClass(DoctorAvailability.class);
    @Mock
    private DoctorAvailabilityRepository availabilityRepository;
    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        assertNotNull(doctorAvailabilityService);
        assertNotNull(availabilityRepository);

    }

//
//    @Test
//    @DisplayName("Get availability by id when UserPrincipal role is ROLE_DOCTOR")
//    void getAvailabilityByUserIdAndWithMatchedAvailabilityId() {
//        when(availabilityRepository.findAllByUserId(USER_ID)).thenReturn(createDummyRecords());
//        when(mapper.createModel(any())).thenReturn(new DoctorAvailability());
//        DoctorAvailability availability = doctorAvailabilityService.getAvailability(3L, doctorPrincipal);
//        assertEquals(availability, availability);
//    }
//
//    @Test
//    @DisplayName("gets availability by id when Userprincipal role is ROLE_ADMIN")
//    void getAvailabilityByIdWithAdmin() {
//        when(availabilityRepository.findById(3L)).thenReturn(Optional.of(DoctorAvailabilityEntity.builder().doctorEntity(null).doctorAvailabilityId(3L).build()));
//        when(mapper.createModel(any())).thenReturn(new DoctorAvailability());
//        DoctorAvailability availability = doctorAvailabilityService.getAvailability(3L, adminPrincipal);
//        assertEquals(availability, availability);
//    }
//
//    @Test
//    @DisplayName("Get availability when doctorAvailability is null")
//    void getAvailabilityByUserIdWhenObjectIsNull() {
//
//        when(availabilityRepository.findAllByUserId(USER_ID)).thenReturn(createDummyRecords());
//        when(mapper.createModel(any())).thenReturn(new DoctorAvailability());
//        assertThrows(ObjectNotFoundException.class, () -> doctorAvailabilityService.getAvailability(5L, doctorPrincipal));
//
//    }
//
//
//    @Test
//    @DisplayName("Get availabilities pageable as admin")
//    void getAvailabilityPageableAsAdmin() {
//        Page<DoctorAvailabilityEntity> pageable = new PageImpl<>(createDummyRecords());
//        when(availabilityRepository.findAll(any(PageRequest.class))).thenReturn(pageable);
//        Page<DoctorAvailability> availabilityPageable = doctorAvailabilityService.getAvailabilityPageable(adminPrincipal, 1, 2);
//        assertEquals(availabilityPageable, availabilityPageable);
//
//        verify(availabilityRepository, times(1)).findAll(any(PageRequest.class));
//    }
//
//    @Test
//    @DisplayName("Get availabilities pageable as doctor")
//    void getAvailabilityPageableAsDoctor() {
//        //given
//        Page<DoctorAvailabilityEntity> pageable = new PageImpl<>(createDummyRecords());
//
//        //when
//        when(availabilityRepository.findAllByDoctorEntityUserUserId(any(), any(PageRequest.class))).thenReturn(pageable);
//
//        //then
//        Page<DoctorAvailability> availabilityPageable = doctorAvailabilityService.getAvailabilityPageable(doctorPrincipal, 1, 1);
//        assertEquals(availabilityPageable, availabilityPageable);
//    }
//
//    @Test
//    @DisplayName("IncorrectTime exception test in updateAvailability within same day")
//    void incorrectTimeExceptionWithWrongHours() {
//        DoctorAvailability doctorAvailability = mock(DoctorAvailability.class);
//        when(doctorAvailability.getStartTime()).thenReturn(LocalDateTime.of(1, 1, 1, 2, 1));
//        when(doctorAvailability.getEndTime()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
//
//        IncorrectTimeException thrown = assertThrows(IncorrectTimeException.class, () -> doctorAvailabilityService.updateAvailability(doctorAvailability, USER_ID));
//        assertNotNull(thrown);
//        assertEquals("StartTime or endTime is incorrect", thrown.getMessage());
//        verifyNoInteractions(availabilityRepository);
//
//    }
//
//    @Test
//    @DisplayName("IncorrectTime exception test in updateAvailability not in same day")
//    void timeIsInSameDayIncorrectTimeException() {
//        DoctorAvailability doctorAvailability = mock(DoctorAvailability.class);
//        when(doctorAvailability.getStartTime()).thenReturn(LocalDateTime.of(1, 1, 1, 2, 1));
//        when(doctorAvailability.getEndTime()).thenReturn(LocalDateTime.of(1, 1, 2, 3, 1));
//        IncorrectTimeException thrown = assertThrows(IncorrectTimeException.class, () -> doctorAvailabilityService.updateAvailability(doctorAvailability, USER_ID));
//
//        assertNotNull(thrown);
//        assertEquals("Method 'availabilityTimeCheck' dates are not the same", thrown.getMessage());
//        verifyNoInteractions(availabilityRepository);
//
//    }
//
//    @Test
//    void noEntityForUpdateAvailabilityThrowsException() {
//        DoctorAvailability doctorAvailability = mock(DoctorAvailability.class);
//        // jeigu uzkomentuoju apatinius du nullpointeris.
//        when(doctorAvailability.getStartTime()).thenReturn(LocalDateTime.of(1, 1, 1, 2, 1));
//        when(doctorAvailability.getEndTime()).thenReturn(LocalDateTime.of(1, 1, 1, 3, 1));
//        when(availabilityRepository.findAllByUserId(anyLong())).thenThrow(new ObjectNotFoundException("Doctor availability entity by id:19 not found"));
//        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> doctorAvailabilityService.updateAvailability(doctorAvailability, USER_ID));
//        assertNotNull(thrown);
//        assertEquals("Doctor availability entity by id:19 not found", thrown.getMessage());
//        verify(availabilityRepository, times(1)).findAllByUserId(USER_ID);
//    }


//    @Test
//    void tryUpdateAvailability() {
//        //when
//        // geriau susikurti realu objekta.
//        DoctorAvailabilityEntity doctorAvailabilityEntity = mock(DoctorAvailabilityEntity.class);
//        DoctorAvailability doctorAvailability = mock(DoctorAvailability.class);
//
//        when(doctorAvailabilityEntity.getDoctorAvailabilityId()).thenReturn(DOCTOR_AVAILABILITY_ID);
//        when(availabilityRepository.findAllByDoctorEntityUserUserId(anyLong())).thenReturn(new ArrayList<>(){{add(doctorAvailabilityEntity);}});
//        when(doctorAvailability.getStartTime()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
//        when(doctorAvailability.getEndTime()).thenReturn(LocalDateTime.of(1, 1, 1, 2, 1));
//        when(doctorAvailability.getDoctorAvailabilityId()).thenReturn(DOCTOR_AVAILABILITY_ID);
//        when(mapper.createModel(any())).thenReturn(doctorAvailability);
////        ArgumentCaptor<DoctorAvailabilityEntity> argumentCaptor = ArgumentCaptor.forClass(DoctorAvailabilityEntity.class);
//        //then
//        DoctorAvailability doctorAvailability1 = doctorAvailabilityService.updateAvailability(doctorAvailability, USER_ID);
//
//
//        verify(availabilityRepository,times(1)).saveAndFlush(doctorAvailabilityEntity);
//        verify(mapper,times(1)).createModel(doctorAvailabilityEntity);
//        assertNotNull(doctorAvailability1);
//
//    }


    private List<DoctorAvailabilityEntity> createDummyRecords() {
        return List.of(DoctorAvailabilityEntity.builder().doctorEntity(null).doctorAvailabilityId(1L).build(),
                DoctorAvailabilityEntity.builder().doctorEntity(null).doctorAvailabilityId(3L).build());
    }

    private UserPrincipal createUserPrincipal(RoleEnum role, Long userId) {
        if (role == RoleEnum.ROLE_ADMIN) {
            return new UserPrincipal(userId, "email@test.com", "ss", "ROLE_ADMIN");
        } else return new UserPrincipal(userId, "email@test.com", "ss", "ROLE_DOCTOR");
    }
}