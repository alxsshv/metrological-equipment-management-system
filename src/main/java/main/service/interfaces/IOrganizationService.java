package main.service.interfaces;

import main.dto.OrganizationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrganizationService {
    ResponseEntity<?> save(OrganizationDto organizationDto);
    ResponseEntity<?> update(OrganizationDto organizationDto);
    ResponseEntity<?>delete(long id);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> findBySearchString(String searchString);
    ResponseEntity<?> findBySearchStringWithPages (String searchString, Pageable pageable);
    Page<OrganizationDto> findAll(Pageable pageable);
    List<OrganizationDto> findAll();
}
