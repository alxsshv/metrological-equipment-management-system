package main.dto.rest.mappers;

import main.dto.rest.RoleDto;
import main.model.Role;

public class RoleDtoMapper {
    public static Role mapToEntity(RoleDto dto){
        Role role = new Role();
        role.setId(dto.getId());
        role.setPseudonym(dto.getPseudonym());
        return role;
    }

    public static RoleDto mapToDto(Role role){
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setPseudonym(role.getPseudonym());
        return dto;
    }


}
