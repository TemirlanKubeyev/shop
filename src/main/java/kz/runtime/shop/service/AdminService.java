package kz.runtime.shop.service;

import kz.runtime.shop.models.Order;
import kz.runtime.shop.models.Review;
import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.OrderStatus;
import kz.runtime.shop.repositories.OrderRepository;
import kz.runtime.shop.repositories.ReviewRepository;
import kz.runtime.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAllByPublished(false);
        return reviews;
    }

    public void publishedReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setPublished(true);
        reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        reviewRepository.delete(review);
    }

    public List<Order> getOrders (OrderStatus status) {
        if(status!=null) {
            List<Order> orders = orderRepository.findAllByStatusOrderByIdAsc(status);
            return orders;
        }else {
            List<Order> orders = orderRepository.findAllByOrderByIdAsc();
            return orders;
        }
    }

    public OrderStatus[] getOrderStatusList() {
        OrderStatus[] orderStatusList = OrderStatus.values();
        return orderStatusList;
    }

    public void editOrderStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(orderStatus);
        orderRepository.save(order);
    }

    public void savePathPhoto(String path) {
        Long id = userService.getCurrentUser().getId();
        User user = userRepository.findById(id).orElseThrow();
        user.setPhotoPath(path);
        userRepository.save(user);
    }

    public String createDirectoryPhotos(MultipartFile photo, String directoryName) throws Exception {
        String staticPath = "C:\\Users\\Kasht\\IdeaProjects\\shop\\";
        String dirPath = staticPath+directoryName;
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
        }else {
            return "EmptyFile";
        }
    }

    public void deletePhotoFromDirectory(Path pathPrevPhoto) throws IOException {
        Files.deleteIfExists(pathPrevPhoto);
    }




}
