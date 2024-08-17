package kz.runtime.shop.service;

import kz.runtime.shop.models.*;
import kz.runtime.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private OptionRepository optionRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public Pageable getPageable(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return pageable;
    }

    public List<Integer> allPages(Pageable pageable) {
        double sizeAllProduct = productRepository.findAll().size();
        List<Integer> pages = new ArrayList<>();
        int page = 1;
        double size = sizeAllProduct / pageable.getPageSize();
        for (int i = 0; i < size; i++) {
            pages.add(page);
            page++;
        }
        return pages;
    }

    public boolean addProduct(String name, Integer price, Category category, List<String> values) {
        for (String value : values) {
            if (value.isEmpty()) {
                return false;
            }
        }
        if (name.isEmpty() || price == null) {
            return false;
        } else {
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
            return true;
        }
    }

    public Product editProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void editProduct(Long id, String name, Integer price, List<String> values, List<Long> options) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);

        int count = 0;
        for (Long optionId : options) {
            Value value = valueRepository.findByOptionIdAndProduct(optionId, product);
            if (value != null) {
                value.setValue(values.get(count));
            } else {
                Value newValue = new Value();
                newValue.setProduct(product);
                Option option = optionRepository.findById(optionId).orElseThrow();
                newValue.setOption(option);
                newValue.setValue(values.get(count));
                valueRepository.save(newValue);
            }
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
                if (value.getProduct() == product) {
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
        if (!(reviews.isEmpty())) {
            float average = 0;
            int count = 0;
            for (int i = 0; i < reviews.size(); i++) {
                average += reviews.get(i).getScore();
                count++;
            }
            average = average / count;
            return average;
        } else {
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
        } else {
            return false;
        }
    }

    public String createDirectoryPhotos(MultipartFile photo, String directoryName) throws Exception {
        String staticPath = "C:\\Users\\Kasht\\IdeaProjects\\shop\\";
        String dirPath = staticPath + directoryName;
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        // создаем уникальное название файла
        String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
        // сохранение пути файла
        Path filePath = path.resolve(fileName);
        // считывание поток байтов файла, сохранение файла в указанный путь и
        // перезапись если имеется похожий файл
        if (!(photo.isEmpty())) {
            Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String pathPhoto = directoryName + fileName;
            return pathPhoto;
        } else {
            return "EmptyFile";
        }
    }

    public void savePathPhoto(String path, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setPhoto(path);
        productRepository.save(product);
    }

    public void deletePhotoFromDirectory(Path pathPrevPhoto) throws IOException {
        Files.deleteIfExists(pathPrevPhoto);
    }

}



