package kz.runtime.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("errorMessage", error != null);
        return "login_page";
    }

}

