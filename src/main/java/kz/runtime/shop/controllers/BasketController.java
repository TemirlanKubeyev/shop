package kz.runtime.shop.controllers;

import kz.runtime.shop.models.Basket;
import kz.runtime.shop.models.User;
import kz.runtime.shop.service.BasketService;
import kz.runtime.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserService userService;
    @GetMapping("/basket")
    public String getBasketDetails(Model model) {
        int total = basketService.getTotalPrice();
        model.addAttribute("total_cost", total);
        User user = userService.getCurrentUser();
        List<Basket> baskets = basketService.getAllProductsInBasket(user);
        model.addAttribute("baskets", baskets);
        return "basket";
    }

    @PostMapping("/basket/{id}/delete")
    public String deleteProductFromBasket(@PathVariable (value = "id") long id) {
        basketService.deleteProductFromBasket(id);
        return "redirect:/basket";
    }

    @PostMapping("/products/{id}/add_basket")
    public String addProductToBasket(@PathVariable (value = "id") long id) {
        basketService.addProductToBasket(id);
        return "redirect:/products?page=1";
    }

    @PostMapping("/basket/{id}/increase")
    public String increaseQuantity(@PathVariable (value = "id") long id) {
        basketService.increaseQuantity(id);
        return "redirect:/basket";
    }

    @PostMapping("/basket/{id}/decrease")
    public String decreaseQuantity(@PathVariable (value = "id") long id) {
        basketService.decreaseQuantity(id);
        return "redirect:/basket";
    }
}
