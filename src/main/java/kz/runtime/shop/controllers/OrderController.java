package kz.runtime.shop.controllers;

import kz.runtime.shop.models.Order;
import kz.runtime.shop.models.OrderProduct;
import kz.runtime.shop.repositories.OrderProductRepository;
import kz.runtime.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        Map<Long, List<OrderProduct>> orderProductMap = new HashMap<>();

        for (Order order : orders) {
            List<OrderProduct> orderProductsList = orderProductRepository.findOrderProductByOrder(order);
            orderProductMap.put(order.getId(), orderProductsList);
        }

        model.addAttribute("order_product_map", orderProductMap);

        return "orders";
    }
}

