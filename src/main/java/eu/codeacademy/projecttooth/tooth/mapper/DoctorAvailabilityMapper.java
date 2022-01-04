package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectDoctorAvailabilityTime;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityMapper {

    private final DoctorEntityRepository doctorEntityRepository;
    private final DoctorAvailabilityEntityRepository availabilityEntityRepository;

    public DoctorAvailabilityEntity createEntity(DoctorAvailability doctorAvailability) {
        availabilityTimeCheck(doctorAvailability);
        return DoctorAvailabilityEntity.builder()
                .startTime(doctorAvailability.getStartTime())
                .endTime(doctorAvailability.getEndTime())
                .doctorEntity(getDoctorEntity(doctorAvailability))
                .build();
    }

    public DoctorAvailability createModel(DoctorAvailabilityEntity entity) {
        return DoctorAvailability.builder()
                .doctorAvailabilityId(entity.getDoctorAvailabilityId())
                .doctorId(entity.getDoctorEntity().getDoctorId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    public DoctorAvailabilityEntity updateEntity(DoctorAvailability doctorAvailability) {
        availabilityTimeCheck(doctorAvailability);
        DoctorAvailabilityEntity entity = availabilityEntityRepository.findById(doctorAvailability.getDoctorAvailabilityId())
                .orElseThrow(() -> new ObjectNotFoundException("DoctorAvailabilityId not found:" + doctorAvailability.getDoctorAvailabilityId()));
        entity.setStartTime(doctorAvailability.getStartTime());
        entity.setEndTime(doctorAvailability.getEndTime());
        return entity;
    }

    private DoctorEntity getDoctorEntity(DoctorAvailability doctorAvailability) {
        return doctorEntityRepository.findById(doctorAvailability.getDoctorId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("DoctorId: %s not found", doctorAvailability.getDoctorId())));
    }

    private void availabilityTimeCheck(DoctorAvailability doctorAvailability) {
        if (!doctorAvailability.getStartTime().isBefore(doctorAvailability.getEndTime()) || !doctorAvailability.getStartTime().toLocalDate().isEqual(doctorAvailability.getEndTime().toLocalDate())) {
            throw new IncorrectDoctorAvailabilityTime("StartTime or endTime is incorrect");
        }
    }
}