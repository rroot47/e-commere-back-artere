package art.fr.service;

import art.fr.constants.ArtereConstant;
import art.fr.dto.ProductRequestDTO;
import art.fr.dto.ProductResponseDTO;
import art.fr.entities.Category;
import art.fr.entities.Product;
import art.fr.mappers.ProductMapper;
import art.fr.repositosy.CategoryRepository;
import art.fr.repositosy.ProductRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper  productMapper;
    private final CategoryRepository categoryRepository;

    private Environment environment;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository, Environment environment) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.environment = environment;
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::productToProductResponseDTO).collect(Collectors.toList());
    }
    public ProductResponseDTO getProduct(Long id) {
        Product product = productRepository.findById(id).get();
        return productMapper.productToProductResponseDTO(product);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).get();
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setAvailable(productRequestDTO.isAvailable());
        Product updateProduct = productRepository.save(product);
        return productMapper.productToProductResponseDTO(updateProduct);
    }

    public ProductResponseDTO saveProduct(Long category_id, ProductRequestDTO productRequestDTO, MultipartFile file) throws IOException {
        Category category = categoryRepository.findById(category_id).get();
        Product product = productMapper.productResponseDTOToProduct(productRequestDTO);
        product.setImagePath(Base64.getEncoder().encodeToString(file.getBytes()));
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return productMapper.productToProductResponseDTO(saveProduct);
    }

    public byte[] downloadPicture(Long id) throws IOException {
        Product product = productRepository.findById(id).get();
        if(product.getId()==null) throw new RuntimeException("Product not found");
        String filePath = product.getImagePath();
        if(filePath==null) throw new RuntimeException("File path not found");
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public String getProductImageDownloadUrl(Long productId){
        // Récupérer le produit par ID depuis le repository
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // Construire et retourner l'URL de téléchargement de l'image
            return "http://localhost:1090"+ArtereConstant.REQUEST_MAPPING+"/product/pic/" + productId;
        }
        return null;
    }

    public String downloadPicture1(Long id) throws IOException {
        Product product = productRepository.findById(id).get();
        if(product.getId()==null) throw new RuntimeException("Product not found");
        String filePath = product.getImagePath();
        if(filePath==null) throw new RuntimeException("File path not found");
        byte[] files =Files.readAllBytes(new File(filePath).toPath());
        return Arrays.toString(files);
    }
}
