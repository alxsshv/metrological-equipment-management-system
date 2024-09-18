package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.dto.rest.RoleDto;
import main.dto.rest.mappers.RoleDtoMapper;
import main.model.Role;
import main.repository.RoleRepository;
import main.service.interfaces.RoleService;
import main.service.validators.RoleAlreadyExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Validated
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(@RoleAlreadyExist @Valid Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleById(long id){
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()){
            throw new EntityNotFoundException("Роль не найдена");
        }
        return roleOpt.get();
    }

    @Override
    public RoleDto findById(long id) {
        return RoleDtoMapper.mapToDto(getRoleById(id));
    }

    @Override
    public RoleDto findByName(String name) {
        Role role = roleRepository.findByName(name);
        return RoleDtoMapper.mapToDto(role);
    }

    @Override
    public Page<RoleDto> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(RoleDtoMapper::mapToDto);
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(RoleDtoMapper::mapToDto).toList();
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }
}
