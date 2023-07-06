package art.fr.mappers;

import art.fr.dto.CategoryRequestDTO;
import art.fr.dto.CategoryResponseDTO;
import art.fr.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryResponseDTO categoryToCategoryResponseDTO(Category  category);
    Category categoryResponseDTOToCategory(CategoryRequestDTO categoryRequestDTO);
}
