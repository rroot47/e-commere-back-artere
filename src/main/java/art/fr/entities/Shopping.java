package art.fr.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int qte;
    @OneToMany(mappedBy = "shopping")
    private List<ProductItem> productItems;
    @OneToOne(mappedBy = "shopping")
    private Customer customer;
}
