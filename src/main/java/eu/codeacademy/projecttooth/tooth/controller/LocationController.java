package eu.codeacademy.projecttooth.tooth.controller;


import eu.codeacademy.projecttooth.tooth.model.Location;
import eu.codeacademy.projecttooth.tooth.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Location> getLocations() {
        return service.getAllLocations();
    }
}
