package art.fr.repositosy;

import art.fr.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    ProductItem findProductItemByProductId(Long id);
    @Query("select pi from ProductItem pi where pi.shopping.id=:id")
    List<ProductItem> getProductItemByCartId(Long id);

    List<ProductItem> findProductItemByShopping_Id(Long id);
}
