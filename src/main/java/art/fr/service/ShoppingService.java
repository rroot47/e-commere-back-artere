package art.fr.service;

import art.fr.dto.ProductItemDTO;
import art.fr.dto.ShoppingAllResponseDTO;
import art.fr.dto.ShoppingResponseDTO;
import art.fr.entities.Customer;
import art.fr.entities.Product;
import art.fr.entities.ProductItem;
import art.fr.entities.Shopping;
import art.fr.mappers.ProductItemMapper;
import art.fr.mappers.ShoppingMapper;
import art.fr.repositosy.CustomerRepository;
import art.fr.repositosy.ProductItemRepository;
import art.fr.repositosy.ProductRepository;
import art.fr.repositosy.ShoppingRespository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingService {

    private final ShoppingRespository shoppingRespository;
    private final ShoppingMapper shoppingMapper;
    private final ProductItemRepository productItemRepository;
    private final ProductItemMapper productItemMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public ShoppingService(ShoppingRespository shoppingRespository, ShoppingMapper shoppingMapper, ProductItemRepository productItemRepository, ProductItemMapper productItemMapper, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.shoppingRespository = shoppingRespository;
        this.shoppingMapper = shoppingMapper;
        this.productItemRepository = productItemRepository;
        this.productItemMapper = productItemMapper;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<ShoppingResponseDTO> getAllShoppings() {
        List<Shopping> shoppings = shoppingRespository.findAll();
        List<ProductItem>  productItems = productItemRepository.findAll();
        List<ProductItemDTO> itemDTO = productItems.stream()
                .map(productItemMapper::productItemToProductItemDTO).toList();
        List<ShoppingResponseDTO> shoppingResponseDTOS = shoppings.stream().map((shoppingMapper::shoppingToShoppingResponseDTO)).toList();
        shoppingResponseDTOS.forEach(item ->item.setProductItem(itemDTO));
        return shoppingResponseDTOS;
    }

    public List<ShoppingAllResponseDTO> getAllShoppingCarts(){
        List<Shopping> shoppings = shoppingRespository.findAll();
        return shoppings.stream().map((shoppingMapper::shoppingToShoppingAllResponseDTO)).toList();
    }

    public ShoppingResponseDTO createShoppingCart(Long id){
        Customer customer = customerRepository.findCustomerById(id);
        Shopping shopping = Shopping.builder()
                .qte(0)
                .productItems(new ArrayList<>())
                .customer(customer)
                .build();
        shoppingRespository.save(shopping);
        Shopping shoppingCart = shoppingRespository.findById(shopping.getId()).get();
        customer.setShopping(shoppingCart);
        customerRepository.save(customer);
        return shoppingMapper.shoppingToShoppingResponseDTO(shopping);
    }

    public ShoppingResponseDTO getCart(Long idCart, Long customer_id) {
        List<ProductItemDTO> productItems = getProductItemByCartId(idCart);
        Customer  customer = customerRepository.findCustomerById(customer_id);
        ShoppingResponseDTO shoppingResponseDTO = new ShoppingResponseDTO();
        shoppingResponseDTO.setId(idCart);
        shoppingResponseDTO.setQte(productItems.size());
        shoppingResponseDTO.setProductItem(productItems);
        shoppingResponseDTO.setCustomer_id(customer.getId());
        return shoppingResponseDTO;
    }

    public List<ProductItemDTO>  getProductItemByCartId(Long id){
        List<ProductItem> productItems =  productItemRepository.findProductItemByShopping_Id(id);
        return productItems.stream().map(productItemMapper::productItemToProductItemDTO).collect(Collectors.toList());
    }
    public ShoppingResponseDTO addProductItemToShopping(Long shopping_id, Long product_id, Long customer_id, int qte){
        Product product = productRepository.findProductById(product_id);
        Customer customer = customerRepository.findCustomerById(customer_id);
        Shopping shopping = shoppingRespository.findShoppingById(shopping_id);
        ProductItem productItem = productItemRepository.findProductItemByProductId(product_id);

        if(customer!=null && !customer.getId().equals(customer_id)){
            ShoppingResponseDTO shoppingResponseDTO = createShoppingCart(customer.getId());
            customer = customerRepository.findById(shoppingResponseDTO.getId()).get();
            shopping = shoppingRespository.findById(shoppingResponseDTO.getId()).get();
            if(productItem.getId()==null){
                productItem = ProductItem.builder()
                        .price(product.getPrice())
                        .qte(qte)
                        .product(product)
                        .shopping(shopping)
                        .build();
            }
        }
        if(shopping != null && productItem==null && customer.getId().equals(customer_id)) {
                productItem = ProductItem.builder()
                        .price(product.getPrice())
                        .qte(qte)
                        .product(product)
                        .shopping(shopping)
                        .build();
        }else{
            assert productItem != null;
            productItem.setQte(productItem.getQte()+qte);
            productItem.setPrice(productItem.getPrice()*qte);
        }
        productItemRepository.save(productItem);
        return getCart(shopping_id, customer_id);
    }
}
