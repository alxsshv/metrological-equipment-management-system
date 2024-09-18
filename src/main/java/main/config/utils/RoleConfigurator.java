package main.config.utils;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.config.AppDefaults;
import main.dto.rest.RoleDto;
import main.dto.rest.mappers.RoleDtoMapper;
import main.model.Role;
import main.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@Component
@Slf4j
public class RoleConfigurator {
    @Autowired
    RoleService roleService;

    public void create(SystemSecurityRoles securityRole){
        Role role = new Role();
        try{
            role.setName("ROLE_"+securityRole.getName());
            role.setPseudonym(securityRole.getPseudonym());
            roleService.save(role);
            log.info("Роль {} добавлена в систему", securityRole.getPseudonym());
        } catch (ConstraintViolationException ex){
            log.warn(ex.getMessage());
        }
    }

    public void createDefaultRole(SystemSecurityRoles securityRole){
        Role defaultRole = new Role();
        try{
            defaultRole.setName("ROLE_"+securityRole.getName());
            defaultRole.setPseudonym(securityRole.getPseudonym());
            roleService.save(defaultRole);
        } catch (ConstraintViolationException ex){
            log.warn(ex.getMessage());

        }
        RoleDto roleDto = roleService.findByName("ROLE_"+securityRole.getName());
        AppDefaults.setDefaultUserRole(RoleDtoMapper.mapToEntity(roleDto));
        log.info("Создана роль, устанавливаемая по умолчанию для новых пользователей");
    }
}
