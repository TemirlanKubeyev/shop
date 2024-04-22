package kz.runtime.shop.service;

import kz.runtime.shop.models.*;
import kz.runtime.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ValueRepository valueRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public Pageable getPageable(int page) {
        Pageable pageable = PageRequest.of(page, 2);
        return pageable;
    }

    public List<Integer> allPages(Pageable pageable) {
        double sizeAllProduct = productRepository.findAll().size();
        List<Integer> pages = new ArrayList<>();
        int page = 1;
        double size = sizeAllProduct/pageable.getPageSize();
        for (int i = 0; i < size; i++) {
            pages.add(page);
            page++;
        }
        return pages;
    }

    public void addProduct(String name, Integer price, Category category, List<String> values) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        productRepository.save(product);

        List<Option> options = product.getCategory().getOption();

        int count = 0;
        for (String value : values) {
            Value newValue = new Value();
            newValue.setValue(value);
            newValue.setProduct(product);
            newValue.setOption(options.get(count));
            count++;
            valueRepository.save(newValue);
        }
    }

    public Product editProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void editProduct(Long id, String name, Integer price, List<String> values) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);
        List<Option> options = product.getCategory().getOption();

        int count = 0;
        for (Option option : options) {
            if (!(option.getValue()==null)) {
                option.getValue().setValue(values.get(count));
            }else {
                Value value = new Value();
                value.setProduct(product);
                value.setOption(option);
                value.setValue(values.get(count));
                valueRepository.save(value);
            }
            count++;
        }
        productRepository.save(product);
    }

    public List<String> getValues(List<Option> options) {
        List<String> values = new ArrayList<>();
        for (Option option : options) {
            System.out.println(option.getValue());
            if(option.getValue()==null){
                values.add("нет значения");
            }else {
                values.add(option.getValue().getValue());
            }
        }
        return values;
    }


    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public float averageScoreReviews(Product product, boolean published) {
        List<Review> reviews = reviewService.getReviewsByProductAndPublished(product, published);
        if(!(reviews.isEmpty())) {
            float average = 0;
            int count = 0;
            for (int i = 0; i < reviews.size(); i++) {
                average += reviews.get(i).getScore();
                count++;
            }
            average = average/count;
            return average;
        }else {
            return 0;
        }
    }

    public boolean deleteProduct(Product product) {
        List<Basket> baskets = basketRepository.findAllByProduct(product);
        List<OrderProduct> orderProducts = orderProductRepository.findAllByProduct(product);
        List<Review> reviews = reviewRepository.findAllByProduct(product);

        if (baskets.isEmpty() && orderProducts.isEmpty() && reviews.isEmpty()) {
            List<Value> values = valueRepository.findAllByProduct(product);
            valueRepository.deleteAll(values);
            productRepository.delete(product);
            return true;
        }else {
            return false;
        }

    }
}



