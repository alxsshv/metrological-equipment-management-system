package main.service.interfaces;

import main.dto.rest.OrganizationDto;
import main.exception.EntityAlreadyExistException;
import main.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {
    void save(OrganizationDto organizationDto) throws EntityAlreadyExistException;
    OrganizationDto findById(long id);
    Organization getOrganizationById(long id);
    List<OrganizationDto> findBySearchString(String searchString);
    Page<OrganizationDto> findBySearchString(String searchString, Pageable pageable);
    Page<OrganizationDto> findAll(Pageable pageable);
    List<OrganizationDto> findAll();
    void update(OrganizationDto organizationDto);
    void delete(long id);

}
