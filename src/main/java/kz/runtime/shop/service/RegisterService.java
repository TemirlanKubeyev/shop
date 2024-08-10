package kz.runtime.shop.service;

import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.UserRole;
import kz.runtime.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class RegisterService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean addUser(String first_name, String last_name, String email, String password) {
        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }
        boolean emailRegex = email.matches("^[A-Za-z]([A-Za-z]*)\\d*\\@([a-z]*)\\.([a-z]*)");

        if (!(emailRegex)) {
            return false;
        }

        if (userRepository.existsUserByEmail(email)) {
            return false;
        }

        if(userRepository.findByEmail(email)==null) {
            User user = new User();
            user.setFirstName(first_name);
            user.setLastName(last_name);
            user.setEmail(email);
            String pass = passwordEncoder.encode(password);
            user.setPassword(pass);
            UserRole userRole = UserRole.USER;
            user.setRole(userRole);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }
}
