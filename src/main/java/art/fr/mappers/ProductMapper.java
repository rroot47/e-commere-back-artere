package art.fr.mappers;

import art.fr.dto.ProductRequestDTO;
import art.fr.dto.ProductResponseDTO;
import art.fr.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "product.category.id", target = "category_id")
    ProductResponseDTO productToProductResponseDTO(Product product);
    Product productResponseDTOToProduct(ProductRequestDTO productRequestDTO);
}
