package art.fr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private String name;
    private double price;
    private int quantity;
    private boolean available;
}

//{"name":"Test", "price":150, "quantity":30, "available":true}