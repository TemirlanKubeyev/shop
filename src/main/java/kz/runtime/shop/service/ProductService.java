package kz.runtime.shop.service;

import kz.runtime.shop.models.*;
import kz.runtime.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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
            Value value = new Value();
            value.setProduct(product);
            value.setOption(option);
            value.setValue(values.get(count));
            valueRepository.save(value);
            count++;
        }
        productRepository.save(product);
    }

    public List<String> getValues(List<Option> options, Product product) {
        HashMap<Long, String> optionValue = new HashMap<>();
        // Записываем все значения как "нет значения"
        for (Option option : options) {
            optionValue.put(option.getId(), "Нет значения");
        }
        // Меняем значения по id продукта
        for (Option option : options) {
            for (Value value : option.getValue()) {
                if (value.getProduct()==product){
                   optionValue.put(option.getId(), value.getValue());
                }
            }
        }
        // Сортируем KeySet по id Options
        List<Long> sortedOptions = new ArrayList<>(optionValue.keySet());
        Collections.sort(sortedOptions);

        // добавляем в список значения отсортированного Options
        List<String> values = new ArrayList<>();
        for (Long sortedOption : sortedOptions) {
            values.add(optionValue.get(sortedOption));
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



