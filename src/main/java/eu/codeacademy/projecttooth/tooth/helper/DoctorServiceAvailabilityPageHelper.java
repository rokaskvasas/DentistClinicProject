package eu.codeacademy.projecttooth.tooth.helper;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Data
@Component
public class DoctorServiceAvailabilityPageHelper {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "doctorAvailabilityServiceId";

    public Pageable getPageable(DoctorServiceAvailabilityPageHelper doctorServiceAvailabilityPageHelper) {
        Sort sort = Sort.by(doctorServiceAvailabilityPageHelper.getSortDirection(), doctorServiceAvailabilityPageHelper.getSortBy());
        return PageRequest.of(doctorServiceAvailabilityPageHelper.getPageNumber(), doctorServiceAvailabilityPageHelper.getPageSize(), sort);
    }

}
