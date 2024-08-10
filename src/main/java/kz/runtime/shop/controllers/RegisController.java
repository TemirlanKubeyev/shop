package kz.runtime.shop.controllers;

import kz.runtime.shop.config.SecurityConfig;
import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.UserRole;
import kz.runtime.shop.repositories.UserRepository;
import kz.runtime.shop.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisController {
    @Autowired
    RegisterService registerService;
    private boolean errorMessage;

    @GetMapping("/register_page")
    public String getRegisterPage(Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "register_page";
    }

    @GetMapping("/register_page/info_page")
    public String getInfoPageRegister() {
        return "info_page_register";
    }

    @PostMapping("/register_page")
    public String addUser(@RequestParam String first_name, @RequestParam String last_name,
                          @RequestParam String email, @RequestParam String password,
                          RedirectAttributes redirectAttributes) {
        if (!(registerService.addUser(first_name,last_name,email,password))) {
            errorMessage = true;
            redirectAttributes.addFlashAttribute("first_name", first_name);
            redirectAttributes.addFlashAttribute("last_name", last_name);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("password", password);
        } else {
            errorMessage = false;
        }

        if (errorMessage) {
            return "redirect:/register_page";
        } else {
            return "redirect:/register_page/info_page";
        }
    }
}
