package eu.codeacademy.projecttooth.tooth.helper;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppointmentPageHelper {

    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "appointmentId";

    public Pageable getPageable(AppointmentPageHelper appointmentPageHelper){
        Sort sort = Sort.by(appointmentPageHelper.getSortDirection(), appointmentPageHelper.getSortBy());
        return PageRequest.of(appointmentPageHelper.pageNumber, appointmentPageHelper.getPageSize(), sort);
    }
}
