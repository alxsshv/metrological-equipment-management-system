package main.service.interfaces;

import main.dto.rest.OrganizationDto;
import main.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrganizationService {
    ResponseEntity<?> save(OrganizationDto organizationDto);
    ResponseEntity<?> findById(long id);
    Organization getOrganizationById(long id);
    ResponseEntity<?> findBySearchString(String searchString);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    Page<OrganizationDto> findAll(Pageable pageable);
    List<OrganizationDto> findAll();
    ResponseEntity<?> update(OrganizationDto organizationDto);
    ResponseEntity<?>delete(long id);

}
