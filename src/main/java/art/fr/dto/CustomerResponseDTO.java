package art.fr.dto;

import art.fr.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private Set<Role> roles;
}
