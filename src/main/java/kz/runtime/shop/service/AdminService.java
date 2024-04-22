package kz.runtime.shop.service;

import kz.runtime.shop.models.Order;
import kz.runtime.shop.models.Review;
import kz.runtime.shop.models.enumeration.OrderStatus;
import kz.runtime.shop.repositories.OrderRepository;
import kz.runtime.shop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

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




}
