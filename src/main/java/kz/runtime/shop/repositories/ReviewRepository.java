package kz.runtime.shop.repositories;

import kz.runtime.shop.models.Product;
import kz.runtime.shop.models.Review;
import kz.runtime.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductAndPublished(Product product, boolean published);
    List<Review> findAllByPublished(boolean published);
    List<Review> findAllByProduct(Product product);
    Review findByUserAndProductAndPublished(User user, Product product, boolean published);
}