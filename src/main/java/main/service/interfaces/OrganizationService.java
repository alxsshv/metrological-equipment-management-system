package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.OrganizationDto;
import main.exception.EntityAlreadyExistException;
import main.model.Organization;
import main.service.validators.OrganizationAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {
    void save(@OrganizationAlreadyExist @Valid OrganizationDto organizationDto) throws EntityAlreadyExistException;
    OrganizationDto findById(long id);
    Organization getOrganizationById(long id);
    List<OrganizationDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString);
    Page<OrganizationDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable);
    Page<OrganizationDto> findAll(Pageable pageable);
    List<OrganizationDto> findAll();
    void update(@Valid OrganizationDto organizationDto);
    void delete(long id);

}
