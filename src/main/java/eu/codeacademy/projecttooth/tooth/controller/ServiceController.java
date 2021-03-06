package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Service;
import eu.codeacademy.projecttooth.tooth.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("service")
@RequiredArgsConstructor
@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping("/services")
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }
}
