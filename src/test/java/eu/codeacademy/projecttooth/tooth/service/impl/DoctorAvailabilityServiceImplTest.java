package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorMapper;
import eu.codeacademy.projecttooth.tooth.mapper.LocationMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {DoctorAvailabilityMapper.class, DoctorMapper.class, LocationMapper.class})
@ExtendWith(SpringExtension.class)
class DoctorAvailabilityServiceImplTest {


    public static final long USER_ID = 19L;

    @InjectMocks
    private DoctorAvailabilityServiceImpl doctorAvailabilityService;
    @Mock
    private DoctorAvailabilityMapper mapper;

    @Mock
    private DoctorAvailabilityRepository availabilityRepository;
    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        assertNotNull(doctorAvailabilityService);
        assertNotNull(availabilityRepository);
    }

    private UserPrincipal createUserPrincipal(RoleEnum role, Long userId) {
        if (role == RoleEnum.ADMIN) {
            return new UserPrincipal(userId, "email@test.com", "ss", "ROLE_ADMIN");
        } else return new UserPrincipal(userId, "email@test.com", "ss", "ROLE_DOCTOR");
    }

    @Test
    void getAvailabilityByUserIdAndWithMatchedAvailabilityId() {
        Mockito.when(availabilityRepository.findAllByDoctorEntityUserUserId(USER_ID)).thenReturn(createDummyRecords());
        Mockito.when(mapper.createModel(ArgumentMatchers.any())).thenReturn(new DoctorAvailability());
        DoctorAvailability availability = doctorAvailabilityService.getAvailability(3L, createUserPrincipal(RoleEnum.DOCTOR, USER_ID));
        assertEquals(availability, availability);
    }

    private List<DoctorAvailabilityEntity> createDummyRecords() {
        return List.of(DoctorAvailabilityEntity.builder().doctorEntity(null).doctorAvailabilityId(1L).build(),
                DoctorAvailabilityEntity.builder().doctorEntity(null).doctorAvailabilityId(3L).build());
    }

    @Test
    void getAvailabilityPageable() {
    }

    @Test
    void getAvailabilityByUserIdWhenObjectIsNull() {

        Mockito.when(availabilityRepository.findAllByDoctorEntityUserUserId(USER_ID)).thenReturn(createDummyRecords());
        Mockito.when(mapper.createModel(ArgumentMatchers.any())).thenReturn(new DoctorAvailability());
        assertThrows(ObjectNotFoundException.class, () -> doctorAvailabilityService.getAvailability(5L, createUserPrincipal(RoleEnum.DOCTOR, USER_ID)));

    }
}