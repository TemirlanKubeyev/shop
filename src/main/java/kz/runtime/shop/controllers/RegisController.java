package kz.runtime.shop.controllers;

import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.UserRole;
import kz.runtime.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register_page")
    public String getRegisterPage() {
        return "register_page";
    }

    @PostMapping("/register_page")
    public String addUser(@RequestParam String first_name, @RequestParam String last_name,
                          @RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setEmail(email);
        user.setPassword(password);
        UserRole userRole = UserRole.USER;
        user.setRole(userRole);
        userRepository.save(user);
        return "redirect:/register_page";
    }


}
