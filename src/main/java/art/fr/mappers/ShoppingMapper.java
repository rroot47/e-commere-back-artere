package art.fr.mappers;

import art.fr.dto.ShoppingAllResponseDTO;
import art.fr.dto.ShoppingResponseDTO;
import art.fr.entities.Shopping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ShoppingMapper {
    ShoppingMapper SHOPPING_MAPPER = Mappers.getMapper(ShoppingMapper.class);
    @Mapping(source = "shopping.customer.id", target = "customer_id")
    ShoppingResponseDTO shoppingToShoppingResponseDTO(Shopping shopping);

    @Mapping(source = "shopping.customer.id", target = "customer_id")
    ShoppingAllResponseDTO shoppingToShoppingAllResponseDTO(Shopping shopping);

}
