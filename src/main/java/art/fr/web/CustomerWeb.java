package art.fr.web;

import art.fr.constants.ArtereConstant;
import art.fr.constants.CustomerConstant;
import art.fr.dto.CustomerLoginRequestDTO;
import art.fr.dto.CustomerLoginResponseDTO;
import art.fr.dto.CustomerRequestDTO;
import art.fr.dto.CustomerResponseDTO;
import art.fr.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ArtereConstant.REQUEST_MAPPING)
@CrossOrigin(ArtereConstant.CROSS_ORIGIN)
@SecurityRequirement(name = ArtereConstant.BEARER_AUTHORIZaTION)
@Tag(name = CustomerConstant.TAG_API_CUSTOMER)
public class CustomerWeb {
    private final CustomerService customerService;


    public CustomerWeb(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public  List<CustomerResponseDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @PostMapping("/customer")
    public CustomerResponseDTO addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        return customerService.saveCustomer(customerRequestDTO);
    }

    @PostMapping("/customer/login")
    public CustomerLoginResponseDTO login(@RequestBody CustomerLoginRequestDTO customerLoginRequestDTO){
        return customerService.signIn(customerLoginRequestDTO);
    }

    @GetMapping("/customer/{idCustomer}")
    public CustomerResponseDTO getCustomer(@PathVariable("idCustomer") Long idCustomer) throws ChangeSetPersister.NotFoundException {
        return customerService.getCustomer(idCustomer);
    }

}
