package art.fr.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private int qte;//dans le Shopping
    @ManyToOne
    private Product product;
    @ManyToOne
    private Shopping shopping;
}
