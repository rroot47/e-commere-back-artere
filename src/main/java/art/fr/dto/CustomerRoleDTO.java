package art.fr.dto;

import art.fr.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRoleDTO {
    private Long customer_id;
    private Long role_id;
    private List<Role> role;
}
