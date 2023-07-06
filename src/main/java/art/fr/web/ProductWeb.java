package art.fr.web;

import art.fr.constants.ArtereConstant;
import art.fr.constants.ProductConstant;
import art.fr.dto.ProductRequestDTO;
import art.fr.dto.ProductResponseDTO;
import art.fr.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ArtereConstant.REQUEST_MAPPING)
@CrossOrigin(ArtereConstant.CROSS_ORIGIN)
@SecurityRequirement(name = ArtereConstant.BEARER_AUTHORIZaTION)
@Tag(name = ProductConstant.TAG_API_PRODUCT)
public class ProductWeb {
    private final ProductService productService;

    public ProductWeb(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ProductConstant.GET_ALL_PRODUCT)
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(ProductConstant.GET_PRODUCT)
    public ProductResponseDTO getProduct(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ProductConstant.ADD_PRODUCT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseDTO addProduct(@PathVariable("category_id") Long category_id, @RequestParam("dataJson") String dataJson, @RequestParam("file") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDTO productRequestDTO = objectMapper.readValue(dataJson, ProductRequestDTO.class);
        return productService.saveProduct(category_id,productRequestDTO,file);
    }

    @PatchMapping (ProductConstant.UPDATE_PRODUCT)
    public ProductResponseDTO updateProduct(@PathVariable("id") Long id,@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id, productRequestDTO);
    }

    @GetMapping(ProductConstant.GET_PICTURE_PRODUCT)
    public ResponseEntity<?> downloadPicture(@PathVariable("id") Long id) throws IOException {
        byte[] picture = productService.downloadPicture(id);
        if(picture==null) throw new EOFException("File path not found");
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(picture);
    }
}
