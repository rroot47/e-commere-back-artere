package art.fr.service;

import art.fr.dto.CustomerLoginRequestDTO;
import art.fr.dto.CustomerLoginResponseDTO;
import art.fr.dto.CustomerRequestDTO;
import art.fr.dto.CustomerResponseDTO;
import art.fr.entities.Customer;
import art.fr.mappers.CustomerMapper;
import art.fr.repositosy.CustomerRepository;
import art.fr.repositosy.RoleRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Lazy
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;


    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Optional<Customer> findClientByEmail(String email){
        return customerRepository.findCustomerByEmail(email);
    }
    public CustomerResponseDTO saveCustomer(CustomerRequestDTO customerRequestDTO){
        Customer customer = customerMapper.CUSTOMER_MAPPER.customerRequestDTOToCustomer(customerRequestDTO);
        customer.setRoles(roleRepository.findRoleByRoleName("USER"));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer customerSave = customerRepository.save(customer);
        return customerMapper.CUSTOMER_MAPPER.customerToCustomerResponseDTO(customerSave);
    }

    public List<CustomerResponseDTO> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::customerToCustomerResponseDTO).collect(Collectors.toList());
    }
    public CustomerResponseDTO getCustomer(Long customer_id) throws ChangeSetPersister.NotFoundException {
        Customer customer= customerRepository.findById(customer_id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return customerMapper.CUSTOMER_MAPPER.customerToCustomerResponseDTO(customer);
    }

    public CustomerLoginResponseDTO signIn(CustomerLoginRequestDTO customerLoginRequestDTO){
        String subject;
        CustomerLoginResponseDTO customerLoginResponseDTO = new CustomerLoginResponseDTO();
        List<String> roles;
        Instant instant=Instant.now();

        Optional<Customer> customer = customerRepository.findCustomerByEmail(customerLoginRequestDTO.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customerLoginRequestDTO.getEmail(), customerLoginRequestDTO.getPassword())
        );

        subject=authentication.getName();
        roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(3, ChronoUnit.HOURS))
                .issuer("auth-service")
                .claim("roles",roles)
                .build();
        String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        customerLoginResponseDTO.setToken(jwtAccessToken);
        customerLoginResponseDTO.setId(customer.get().getId());
        customerLoginResponseDTO.setUsername(customer.get().getFirstName());
        customerLoginResponseDTO.setEmail(customer.get().getEmail());
        customerLoginResponseDTO.setRoles(roles);
        return customerLoginResponseDTO;
    }

}
