package kz.runtime.shop.controllers;

import kz.runtime.shop.models.Order;
import kz.runtime.shop.models.OrderProduct;
import kz.runtime.shop.models.Review;
import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.OrderStatus;
import kz.runtime.shop.repositories.OrderProductRepository;
import kz.runtime.shop.repositories.OrderRepository;
import kz.runtime.shop.service.AdminService;
import kz.runtime.shop.service.OrderService;
import kz.runtime.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/admin/reviews")
    public String getAllReviews(Model model) {
        List<Review> reviews = adminService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "/all_reviews";
    }

    @PostMapping("/admin/reviews/published/{id}")
    public String publishedReview(@PathVariable(name = "id") Long id) {
        adminService.publishedReview(id);
        return "redirect:/admin/reviews";
    }

    @PostMapping("/admin/reviews/delete/{id}")
    public String deleteReview(@PathVariable(name = "id") Long id) {
        adminService.deleteReview(id);
        return "redirect:/admin/reviews";
    }

    @GetMapping("/admin/orders")
    public String getAllOrdersAdmin(
            @RequestParam(name = "status", required = false) OrderStatus status, Model model) {
        List<Order> orders = adminService.getOrders(status);
        OrderStatus[] orderStatusArray = adminService.getOrderStatusList();
        model.addAttribute("orders", orders);
        model.addAttribute("orderStatus", orderStatusArray);
        return "all_orders_admin";
    }

    @PostMapping("/admin/orders/status/{id}")
    public String editOrderStatus(@PathVariable(name = "id") Long id, @RequestParam OrderStatus orderStatus) {
        adminService.editOrderStatus(id, orderStatus);
        return "redirect:/admin/orders";
    }


    @GetMapping("/admin/homePage")
    public String getAdminPage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        int totalPrice = orderService.getTotalPriceAllOrders();
        model.addAttribute("totalPrice", totalPrice);
        return "admin_page";
    }

    @GetMapping("/admin/homePage/{id}/details")
    public String getDetailsOrders(@PathVariable (value = "id") Long id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        model.addAttribute("order", order);
        List<OrderProduct> orderProductsList = orderProductRepository.findOrderProductByOrder(order);
        model.addAttribute("order_products", orderProductsList);
        int totalCost = orderService.getTotalCostOrderProducts(orderProductsList);
        model.addAttribute("totalCost", totalCost);
        return "admin_order_details";
    }


}

