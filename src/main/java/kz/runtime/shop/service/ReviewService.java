package kz.runtime.shop.service;

import kz.runtime.shop.models.Product;
import kz.runtime.shop.models.Review;
import kz.runtime.shop.models.User;
import kz.runtime.shop.repositories.ProductRepository;
import kz.runtime.shop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewRepository reviewRepository;

    public void addReview(Long id, String text, float score) {
        Product product = productRepository.findById(id).orElseThrow();
        User user = userService.getCurrentUser();
        Review review = new Review();
        review.setText(text);
        review.setScore(score);
        review.setUser(user);
        review.setProduct(product);
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByProductAndPublished(Product product, boolean published) {
        return reviewRepository.findAllByProductAndPublished(product, published);
    }

}
