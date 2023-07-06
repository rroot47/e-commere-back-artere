package art.fr.service;

import art.fr.dto.CustomerRoleDTO;
import art.fr.dto.CustomerRoleRequestDTO;
import art.fr.dto.RoleRequestDTO;
import art.fr.dto.RoleResponseDTO;
import art.fr.entities.Customer;
import art.fr.entities.Role;
import art.fr.mappers.RoleMapper;
import art.fr.repositosy.CustomerRepository;
import art.fr.repositosy.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(CustomerRepository customerRepository, RoleRepository roleRepository, RoleMapper roleMapper) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public CustomerRoleDTO addRoleToClient(CustomerRoleRequestDTO customerRoleRequestDTO) {
        CustomerRoleDTO customerRoleDTO = new CustomerRoleDTO();
        List<Role> roleList = new ArrayList<>();
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customerRoleRequestDTO.getEmail());
        Role role = roleRepository.getRole(customerRoleRequestDTO.getRoleName());
        Customer customer = customerRepository.findById(customerOptional.get().getId()).get();
        customer.getRoles().add(role);
        customerRepository.save(customer);
        roleList.add(role);
        customerRoleDTO.setCustomer_id(customer.getId());
        customerRoleDTO.setRole_id(role.getId());
        customerRoleDTO.setRole(roleList);
        return customerRoleDTO;
    }

    public RoleResponseDTO saveRole(RoleRequestDTO roleRequestDTO){
        Role role  = roleMapper.ROLE_MAPPER.roleRequestDTOToRole(roleRequestDTO);
        Role saveRole=  roleRepository.save(role);
        return  roleMapper.ROLE_MAPPER.roleToRoleResponseDTO(saveRole);
    }

    public List<RoleResponseDTO> getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::roleToRoleResponseDTO).collect(Collectors.toList());
    }
}
