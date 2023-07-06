package art.fr.repositosy;

import art.fr.entities.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingRespository extends JpaRepository<Shopping, Long> {
    Shopping findShoppingById(Long id);
}
