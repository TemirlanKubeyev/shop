package kz.runtime.shop.service;

import kz.runtime.shop.models.*;
import kz.runtime.shop.models.enumeration.OrderStatus;
import kz.runtime.shop.repositories.BasketRepository;
import kz.runtime.shop.repositories.OrderProductRepository;
import kz.runtime.shop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserService userService;

    public List<Order> getAllOrders() {
        User currentUser = userService.getCurrentUser();
        return orderRepository.findAllByUserOrderByIdAsc(currentUser);
    }

    public void addOrderFormBaskets(User user, List<Basket> baskets, String address) {
        if(baskets != null) {
            Order order = new Order();
            OrderStatus orderStatus = OrderStatus.CREATED;
            order.setUser(user);
            order.setOrderDate(new Date());
            order.setAddress(address);
            order.setStatus(orderStatus);
            orderRepository.save(order);
            for (Basket basket : baskets) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(basket.getProduct());
                orderProduct.setQuantityProducts(basket.getQuantity());
                orderProductRepository.save(orderProduct);
                basketRepository.delete(basket);
            }
        }
    }

    public int getTotalPriceAllOrders() {
        User currentUser = userService.getCurrentUser();
        List<Order> orders = orderRepository.findAllByUserOrderByIdAsc(currentUser);
        int totalPrice = 0;
        for (Order order : orders) {
            List<OrderProduct> orderProductsList = orderProductRepository.findOrderProductByOrder(order);
            for (OrderProduct orderProduct : orderProductsList) {
                totalPrice += orderProduct.getProduct().getPrice() * orderProduct.getQuantityProducts();
            }
        }
        return totalPrice;
    }

    public int getTotalCostOrderProducts(List<OrderProduct> orderProduct) {
        int totalCost = 0;
        for (OrderProduct product : orderProduct) {
            totalCost += product.getQuantityProducts()*product.getProduct().getPrice();
        }
        return totalCost;
    }
}