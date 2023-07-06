package art.fr.mappers;

import art.fr.dto.RoleRequestDTO;
import art.fr.dto.RoleResponseDTO;
import art.fr.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);
    RoleResponseDTO roleToRoleResponseDTO(Role role);
    Role roleRequestDTOToRole(RoleRequestDTO  roleRequestDTO);
}
