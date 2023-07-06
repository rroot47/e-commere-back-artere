package art.fr.web;

import art.fr.constants.ArtereConstant;
import art.fr.constants.RoleConstant;
import art.fr.dto.CustomerRoleDTO;
import art.fr.dto.CustomerRoleRequestDTO;
import art.fr.dto.RoleRequestDTO;
import art.fr.dto.RoleResponseDTO;
import art.fr.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ArtereConstant.REQUEST_MAPPING)
@CrossOrigin(ArtereConstant.CROSS_ORIGIN)
@SecurityRequirement(name = ArtereConstant.BEARER_AUTHORIZaTION)
@Tag(name = RoleConstant.TAG_API_ROLE)
public class RoleWeb {

    private final RoleService roleService;

    public RoleWeb(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping(RoleConstant.GET_ALL_ROLE)
    public List<RoleResponseDTO> getAllRoles(){
        return roleService.getAllRoles();
    }
    @PostMapping(RoleConstant.CREATE_ROLE)
    public RoleResponseDTO addRole(@RequestBody RoleRequestDTO roleName){
        return roleService.saveRole(roleName);
    }

    @PostMapping(RoleConstant.ADD_ROLE_TO_CUSTOMER)
    public CustomerRoleDTO addRoleToClient(@RequestBody CustomerRoleRequestDTO customerRoleRequestDTO) {
        return roleService.addRoleToClient(customerRoleRequestDTO);
    }
}
