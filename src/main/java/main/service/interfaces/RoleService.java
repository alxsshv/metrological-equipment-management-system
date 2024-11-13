package main.service.interfaces;

import jakarta.validation.Valid;
import main.dto.rest.RoleDto;
import main.model.Role;
import main.service.validators.RoleAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface RoleService {
    void save(@RoleAlreadyExist @Valid Role role);
    RoleDto findById(long id);
    RoleDto findByName(String name);
    Role getRoleById(long id);
    Role getRoleByName(String name);
    Page<RoleDto> findAll(Pageable pageable);
    List<RoleDto> findAll();
    void delete(long id);

}
