package art.fr.web;

import art.fr.constants.ArtereConstant;
import art.fr.constants.ShoppingConstant;
import art.fr.dto.ShoppingAllResponseDTO;
import art.fr.dto.ShoppingResponseDTO;
import art.fr.service.ShoppingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ArtereConstant.REQUEST_MAPPING)
@CrossOrigin(ArtereConstant.CROSS_ORIGIN)
@SecurityRequirement(name = ArtereConstant.BEARER_AUTHORIZaTION)
@Tag(name = ShoppingConstant.TAG_SHOPPING_CART)
public class ShoppingWeb {

    private final ShoppingService shoppingService;

    public ShoppingWeb(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping(ShoppingConstant.GET_ALL_SHOPPING_CART)
    public List<ShoppingAllResponseDTO> getAllShopping() {
        return shoppingService.getAllShoppingCarts();
    }

    @GetMapping(ShoppingConstant.GET_SHOPPING_CART)
    public ShoppingResponseDTO getCart(@PathVariable("shopping_id") Long shopping_id, @PathVariable("customer_id") Long customer_id){
        return shoppingService.getCart(shopping_id, customer_id);
    }

    @PostMapping(ShoppingConstant.CREATE_SHOPPING_CART)
    public ShoppingResponseDTO createCart(@PathVariable Long customer_id){
        return shoppingService.createShoppingCart(customer_id);
    }

    @PostMapping(ShoppingConstant.ADD_PRODUCT_TO_SHOPPING_CART)
    public ShoppingResponseDTO addProductToCart(@PathVariable("shopping_id") Long shopping_id, @PathVariable("product_id") Long product_id, @PathVariable("customer_id") Long customer_id, @RequestParam int qte){
        return shoppingService.addProductItemToShopping(shopping_id,product_id,customer_id,qte);
    }
}
