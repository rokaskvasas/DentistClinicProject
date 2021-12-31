package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.DoctorByIdNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectDoctorAvailabilityTime;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.repository.DoctorEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityEntityMapper {

    private final DoctorEntityRepository doctorEntityRepository;

    public DoctorAvailabilityEntity getEntity(DoctorAvailability doctorAvailability) {
        if (!doctorAvailability.getStartTime().isBefore(doctorAvailability.getEndTime())) {
            throw new IncorrectDoctorAvailabilityTime("StartTime or endTime is incorrect");
        }
        return DoctorAvailabilityEntity.builder()
                .startTime(doctorAvailability.getStartTime())
                .endTime(doctorAvailability.getEndTime())
                .doctorEntity(getDoctorEntity(doctorAvailability))
                .build();
    }

    public DoctorAvailability getModel(DoctorAvailabilityEntity entity) {
        return DoctorAvailability.builder()
                .doctorAvailabilityId(entity.getDoctorAvailabilityId())
                .doctorId(entity.getDoctorEntity().getDoctorId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    private DoctorEntity getDoctorEntity(DoctorAvailability doctorAvailability) {
        return doctorEntityRepository.findById(doctorAvailability.getDoctorId())
                .orElseThrow(() -> new DoctorByIdNotFoundException(String.format("Doctor by id: %s not found", doctorAvailability.getDoctorId())));
    }
}
