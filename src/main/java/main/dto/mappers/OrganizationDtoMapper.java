package main.dto.mappers;

import main.dto.OrganizationDto;
import main.model.Organization;

public class OrganizationDtoMapper {
    public static Organization mapToEntity(OrganizationDto dto){
        Organization organization = new Organization();
        organization.setId(dto.getId());
        organization.setTitle(dto.getTitle());
        organization.setNotation(dto.getNotation());
        organization.setAddress(dto.getAddress());
        return organization;
    }

    public static OrganizationDto mapToDto(Organization organization){
        OrganizationDto dto = new OrganizationDto();
        dto.setId(organization.getId());
        dto.setTitle(organization.getTitle());
        dto.setNotation(organization.getNotation());
        dto.setAddress(organization.getAddress());
        return dto;
    }
}
