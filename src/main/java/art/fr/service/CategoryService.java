package art.fr.service;

import art.fr.dto.CategoryRequestDTO;
import art.fr.dto.CategoryResponseDTO;
import art.fr.entities.Category;
import art.fr.mappers.CategoryMapper;
import art.fr.repositosy.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponseDTO> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::categoryToCategoryResponseDTO).collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategory(Long id){
        Category  category= categoryRepository.findById(id).get();
        return categoryMapper.CATEGORY_MAPPER.categoryToCategoryResponseDTO(category);
    }

    public CategoryResponseDTO saveCategory(CategoryRequestDTO categoryRequestDTO){
        Category category = categoryMapper.categoryResponseDTOToCategory(categoryRequestDTO);
        Category saveCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponseDTO(saveCategory);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO){
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(value -> value.setNameCategory(categoryRequestDTO.getNameCategory()));
        Category saveCategory = categoryRepository.save(category.get());
        return categoryMapper.categoryToCategoryResponseDTO(saveCategory);
    }
}
