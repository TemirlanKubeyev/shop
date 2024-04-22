package kz.runtime.shop.service;

import kz.runtime.shop.models.User;
import kz.runtime.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final long CURRENT_USER_ID = 3;

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findById(CURRENT_USER_ID).orElse(null);
    }

}
