package art.fr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingResponseDTO {
    private Long id;
    private int qte;
    private List<ProductItemDTO> productItem;
    private Long customer_id;
}
