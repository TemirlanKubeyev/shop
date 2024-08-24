package kz.runtime.shop.repositories;

import kz.runtime.shop.models.Basket;
import kz.runtime.shop.models.Product;
import kz.runtime.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByProduct(Product product);
    List<Basket> findAllByProductAndUser(Product product, User user);
    List<Basket> findAllByUserOrderByIdAsc(User user);
    Basket findByProductIdAndUser(Long productId, User user);
}