package kz.runtime.shop.service;

import kz.runtime.shop.models.Basket;
import kz.runtime.shop.models.Product;
import kz.runtime.shop.models.User;
import kz.runtime.shop.repositories.BasketRepository;
import kz.runtime.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    public int getTotalPrice() {
        User user = userService.getCurrentUser();
        List<Basket> baskets = basketRepository.findAllByUserOrderByIdAsc(user);
        int total = 0;
        for (int i = 0; i < baskets.size(); i++) {
            total += baskets.get(i).getProduct().getPrice() * baskets.get(i).getQuantity();
        }
        return total;
    }

    public List<Basket> getAllProductsInBasket(User user) {
        List<Basket> baskets = basketRepository.findAllByUserOrderByIdAsc(user);
        if(!(baskets.isEmpty())) {
            return baskets;
        }
        return null;
    }

    public void deleteProductFromBasket(Long id) {
        Basket basket = basketRepository.findById(id).orElseThrow();
        basketRepository.delete(basket);
    }

    public void addProductToBasket(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        User user = userService.getCurrentUser();
        if(basketRepository.findAllByProductAndUser(product, user).isEmpty()) {
            Basket basket = new Basket();
            basket.setUser(user);
            basket.setProduct(product);
            basket.setQuantity(1);
            basketRepository.save(basket);
        }
    }

    public boolean findProductInBasket(Long productId) {
        User user = userService.getCurrentUser();
        Basket basket = basketRepository.findByProductIdAndUser(productId, user);
        if (basket!=null) {
            return true;
        }else {
            return false;
        }
    }

    public void increaseQuantity(Long id) {
        Basket basket = basketRepository.findById(id).orElseThrow();
        basket.setQuantity(basket.getQuantity()+1);
        basketRepository.save(basket);
    }

    public void decreaseQuantity(Long id) {
        Basket basket = basketRepository.findById(id).orElseThrow();
        if(basket.getQuantity()>1) {
            basket.setQuantity(basket.getQuantity()-1);
            basketRepository.save(basket);
        }else {
            deleteProductFromBasket(basket.getId());
        }
    }
}
