package kz.runtime.shop.controllers;

import kz.runtime.shop.models.Basket;
import kz.runtime.shop.models.Order;
import kz.runtime.shop.models.OrderProduct;
import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.OrderStatus;
import kz.runtime.shop.repositories.BasketRepository;
import kz.runtime.shop.repositories.OrderProductRepository;
import kz.runtime.shop.repositories.OrderRepository;
import kz.runtime.shop.service.BasketService;
import kz.runtime.shop.service.OrderService;
import kz.runtime.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class OrderProductController {
    @Autowired
    private UserService userService;
    @Autowired
    private BasketService basketService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/basket/add_to_order_product")
    public String addOrderProducts(@RequestParam String address) {
        if (address.isEmpty()) {
            return "redirect:/basket?addressEmpty=true";
        }
        User user = userService.getCurrentUser();
        List<Basket> baskets = basketService.getAllProductsInBasket(user);
        orderService.addOrderFormBaskets(user, baskets, address);
        return "redirect:/basket";
    }

}


