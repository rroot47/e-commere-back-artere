package art.fr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginResponseDTO {
    private Long id;
    private String token;
    private String username;
    private String email;
    private List<String> roles;
}
