package art.fr.mappers;

import art.fr.dto.ProductItemDTO;
import art.fr.entities.ProductItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ProductItemMapper {
    ProductItemMapper PRODUCT_ITEM_MAPPER = Mappers.getMapper(ProductItemMapper.class);
    @Mapping(source = "productItem.product.id", target = "productId")
    ProductItemDTO productItemToProductItemDTO(ProductItem productItem);
}
