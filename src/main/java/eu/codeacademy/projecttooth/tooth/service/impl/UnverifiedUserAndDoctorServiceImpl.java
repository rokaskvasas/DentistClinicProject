package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import eu.codeacademy.projecttooth.tooth.service.UnverifiedUserAndDoctorService;
import eu.codeacademy.projecttooth.tooth.service.UserService;
import eu.codeacademy.projecttooth.tooth.specification.UnverifiedSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnverifiedUserAndDoctorServiceImpl implements UnverifiedUserAndDoctorService {

    private final UserService userService;
    private final DoctorService doctorService;
    private final UnverifiedSpecification unverifiedSpecification;




    @Override
    public void deleteUnverifiedUsersAndDoctors() {
        doctorService.deleteUnverifiedDoctors(unverifiedDoctors());
        userService.deleteUnverifiedUsers(unverifiedUsers());
    }

    private Iterable<UserEntity> unverifiedUsers() {
        return () -> userService.findAllUsersWithSpecification(unverifiedSpecification.findUnverifiedUsers()).iterator();
    }

    private Iterable<DoctorEntity> unverifiedDoctors() {
        return () -> doctorService.findAllDoctorsWithSpecification(unverifiedSpecification.findUnverifiedDoctors()).iterator();
    }

}
