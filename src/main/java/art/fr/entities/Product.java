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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private boolean available;
    @Lob
    @Column(columnDefinition ="MEDIUMBLOB")
    private String imagePath;
    @OneToMany(mappedBy = "product")
    private List<ProductItem> productItems;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
