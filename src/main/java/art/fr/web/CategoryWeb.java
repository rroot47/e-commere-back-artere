package art.fr.web;

import art.fr.constants.ArtereConstant;
import art.fr.constants.CategoryConstant;
import art.fr.constants.ProductConstant;
import art.fr.dto.CategoryRequestDTO;
import art.fr.dto.CategoryResponseDTO;
import art.fr.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ArtereConstant.REQUEST_MAPPING)
@CrossOrigin(ArtereConstant.CROSS_ORIGIN)
@SecurityRequirement(name = ArtereConstant.BEARER_AUTHORIZaTION)
@Tag(name = CategoryConstant.TAG_API_CATEGORY)
public class CategoryWeb {

    private final CategoryService categoryService;

    public CategoryWeb(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(CategoryConstant.GET_ALL_CATEGORY)
    public List<CategoryResponseDTO> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping(CategoryConstant.GET_CATEGORY)
    public CategoryResponseDTO getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }

    @PostMapping(CategoryConstant.CREATE_CATEGORY)
    public CategoryResponseDTO addCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        return categoryService.saveCategory(categoryRequestDTO);
    }

    @PatchMapping(CategoryConstant.EDIT_CATEGORY)
    public CategoryResponseDTO editCategory(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO categoryRequestDTO){
        return categoryService.updateCategory(id, categoryRequestDTO);
    }
}
